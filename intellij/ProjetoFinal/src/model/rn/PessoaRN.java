/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package model.rn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.dao.ConexaoDAO;
import model.dao.PessoaDAO;
import model.dao.LogDAO;
import model.vo.PessoaVO;
import model.vo.LogVO;

public class PessoaRN {

    private PessoaDAO pessoaDAO;
    private LogDAO logDAO;

    public PessoaRN() throws SQLException {
        this.pessoaDAO = new PessoaDAO(null); // A conexao sera injetada no metodo
        this.logDAO = new LogDAO(null); // A conexao sera injetada no metodo
    }

    // add pessoa
    public void adicionarPessoa(PessoaVO pessoa, int funcionarioId) throws Exception {
        Connection conexao = null;
        try {
            // A RN abre a conexao e inicia a transacao
            conexao = ConexaoDAO.getConexao();
            conexao.setAutoCommit(false);

            // Injeta a conexao nos DAOs
            this.pessoaDAO = new PessoaDAO(conexao);
            this.logDAO = new LogDAO(conexao);

            // 1. Validacao de campos
            if (pessoa.getPes_cpf() == null || pessoa.getPes_cpf().isEmpty()) {
                throw new Exception("CPF e eh um campo obrigatorio.");
            }

            if (pessoa.getPes_nome() == null || pessoa.getPes_nome().isEmpty()){
                throw new Exception("Nome e um campo obrigatorio.");
            }

            // 2. Verifica se o CPF ja existe
            if (pessoaDAO.buscarPorCpf(pessoa.getPes_cpf()) != null) {
                throw new Exception("O CPF informado ja esta cadastrado.");
            }

            // 3. Adiciona a pessoa no banco de dados
            pessoaDAO.adicionarNovo(pessoa);

            // 4. Registra a acao no log
            LogVO log = new LogVO();
            log.setLog_acao("Pessoa adicionada: " + pessoa.getPes_nome());
            log.setLog_fnc_id(funcionarioId);
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

            conexao.commit(); // Finaliza a transacao com sucesso

        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback(); // Desfaz a transacao em caso de erro
            }
            throw new Exception("Erro ao adicionar pessoa: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close(); // Fecha a conexao no final
            }
        }
    }

    public void atualizarPessoa(PessoaVO pessoa, int funcionarioId) throws Exception {
        Connection conexao = null;
        try {
            conexao = ConexaoDAO.getConexao();
            conexao.setAutoCommit(false);

            this.pessoaDAO = new PessoaDAO(conexao);
            this.logDAO = new LogDAO(conexao);

            if (pessoa.getPes_cpf() == null || pessoa.getPes_cpf().isEmpty()) {
                throw new Exception("CPF eh um campo obrigatorio.");
            }

            if (pessoa.getPes_nome() == null || pessoa.getPes_nome().isEmpty()){
                throw new Exception("Nome eh um campo obrigatorio.");
            }

            // 2. Verifica se a pessoa existe para ser atualizada
            if (pessoaDAO.buscarPorCpf(pessoa.getPes_cpf()) == null) {
                throw new Exception("Pessoa nao encontrada para atualizacao.");
            }

            pessoaDAO.atualizar(pessoa);

            // 4. Registra a acao no log
            LogVO log = new LogVO();
            log.setLog_acao("Pessoa atualizada: " + pessoa.getPes_nome());
            log.setLog_fnc_id(funcionarioId);
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

            conexao.commit(); // Finaliza a transacao com sucesso

        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback(); // Desfaz a transacao em caso de erro
            }
            throw new Exception("Erro ao atualizar pessoa: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close(); // Fecha a conexao no final
            }
        }
    }

    public void deletarPessoa(String cpf, int funcionarioId) throws Exception {
        Connection conexao = null;
        try {
            conexao = ConexaoDAO.getConexao();
            conexao.setAutoCommit(false);

            this.pessoaDAO = new PessoaDAO(conexao);
            this.logDAO = new LogDAO(conexao);

            if (cpf == null || cpf.isEmpty()) {
                throw new Exception("CPF eh um campo obrigatorio.");
            }

            // 2. Verifica se a pessoa existe para ser atualizada
            PessoaVO pessoa = pessoaDAO.buscarPorCpf(cpf);
            if (pessoa == null) {
                throw new Exception("Pessoa nao encontrada para delecao.");
            }

            pessoaDAO.deletar(pessoa);

            LogVO log = new LogVO();
            log.setLog_acao("Pessoa deletada: " + pessoa.getPes_nome());
            log.setLog_fnc_id(funcionarioId);
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback(); // Desfaz a transacao em caso de erro
            }
            throw new Exception("Erro ao deletar pessoa: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close(); // Fecha a conexao no final
            }
        }

    }

    public PessoaVO buscarPessoaPorCpf(String cpf, int funcionarioId) throws Exception {
        Connection conexao = null;
        PessoaVO pessoa = null;
        try {
            conexao = ConexaoDAO.getConexao();

            this.pessoaDAO = new PessoaDAO(conexao);
            this.logDAO = new LogDAO(conexao);

            if (cpf == null || cpf.isEmpty()) {
                throw new Exception("CPF eh um campo obrigatorio.");
            }

            pessoa = pessoaDAO.buscarPorCpf(cpf);

            LogVO log = new LogVO();
            log.setLog_acao("Pessoa buscada por CPF: " + cpf);
            log.setLog_fnc_id(funcionarioId);
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar pessoa: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close(); // Fecha a conexao no final
            }
        }
        return pessoa;
    }

    public List<PessoaVO> buscarTodasAsPessoas(String nome) throws Exception {
        Connection conexao = null;
        List<PessoaVO> pessoas = null;
        try {
            conexao = ConexaoDAO.getConexao();

            this.pessoaDAO = new PessoaDAO(conexao);
            this.logDAO = new LogDAO(conexao);

            if (nome == null || nome.isEmpty()) {
                pessoas = pessoaDAO.buscarPorNome("%");
            } else {
                pessoas = pessoaDAO.buscarPorNome(nome);
            }

            LogVO log = new LogVO();
            log.setLog_acao("Busca de pessoas por nome: " + (nome == null || nome.isEmpty() ? "Todas" : nome));
            log.setLog_fnc_id(1); // Substitua por funcionarioId
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar pessoas: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close(); // Fecha a conexao no final
            }
        }
        return pessoas;
    }
}

