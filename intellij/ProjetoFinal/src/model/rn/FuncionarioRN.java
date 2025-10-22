/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package model.rn;

import model.dao.ConexaoDAO;
import model.dao.FuncionarioDAO;
import model.dao.LogDAO;

import model.vo.FuncionarioVO;
import model.vo.LogVO;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioRN {

    private FuncionarioDAO funcionarioDAO;
    private LogDAO logDAO;
    private PessoaDAO pessoaDAO;

    public FuncionarioRN() {}

    public void adicionarFuncionario(FuncionarioVO funcionario) throws Exception {
        Connection conn = null;

        try {
            conn = ConexaoDAO.getConexao();
            conn.setAutoCommit(false);

            this.funcionarioDAO = new FuncionarioDAO(conn);
            this.pessoaDAO = new PessoaDAO(conn);

            // 🔹 1️⃣ Validações básicas
            if (funcionario.getPes_cpf() == null || funcionario.getPes_cpf().isEmpty())
                throw new Exception("CPF é obrigatório.");
            if (funcionario.getPes_nome() == null || funcionario.getPes_nome().isEmpty())
                throw new Exception("Nome é obrigatório.");
            if (funcionario.getPes_sexo() == null || funcionario.getPes_sexo().getSex_id() == 0)
                throw new Exception("Sexo inválido ou não informado.");

            // 🔹 2️⃣ Verifica se a pessoa já existe no banco
            PessoaVO pessoaExistente = pessoaDAO.buscarPorCpf(funcionario.getPes_cpf());

            if (pessoaExistente == null) {
                // Pessoa não existe — então adiciona
                System.out.println("Inserindo nova pessoa com CPF: " + funcionario.getPes_cpf());
                pessoaDAO.adicionarNovo(funcionario);
            } else {
                // Pessoa já existe — opcionalmente atualiza dados
                System.out.println("Pessoa já existe, atualizando cadastro: " + funcionario.getPes_cpf());
                pessoaDAO.atualizar(funcionario);
            }

            // 🔹 3️⃣ Agora adiciona o funcionário
            System.out.println("Adicionando funcionário vinculado ao CPF: " + funcionario.getPes_cpf());
            funcionarioDAO.adicionarNovo(funcionario);

            conn.commit();
            System.out.println("✅ Transação concluída com sucesso.");

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw new Exception("Erro ao adicionar funcionário: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    }




    // att funcionario
    public void atualizarFuncionario(FuncionarioVO funcionario, int funcionarioID) throws Exception {
        Connection conn = null;
        try {
            conn = ConexaoDAO.getConexao();
            conn.setAutoCommit(false);

            this.pessoaDAO = new PessoaDAO(conn);
            this.logDAO = new LogDAO(conn);
            this.funcionarioDAO = new FuncionarioDAO(conn);

            // 1 validacao de campos
            if (funcionario.getPes_cpf() == null || funcionario.getPes_cpf().isEmpty()) {
                throw new Exception("CPF eh um campo obrigatorio");
            }
            if (funcionario.getPes_nome() == null || funcionario.getPes_nome().isEmpty()) {
                throw new Exception("Nome eh um campo obrigatorio");
            }

            // 2 verifica se o funcionario ja existe
            FuncionarioVO funcionarioExistente = funcionarioDAO.buscarPorCpf(funcionario.getPes_cpf());
            if (funcionarioExistente == null) {
                throw new Exception("Funcionario nao encontrado para atualizacao.");
            }

            // 3 atualiza os dados na tabela de pessoa e de funcionario
            pessoaDAO.atualizar(funcionario);
            funcionarioDAO.atualizar(funcionario);

            // 4 registra a acao no log
            LogVO log = new LogVO();
            log.setLog_acao("Funcionario atualizado: " + funcionario.getPes_nome());
            log.setLog_fnc_id(funcionarioID);
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            LogVO logErro = new LogVO();
            logErro.setLog_acao("ERRO: " + e.getMessage());
            logErro.setLog_fnc_id(funcionarioID);
            logErro.setLog_dataHora(java.time.LocalDateTime.now());
            try {
                logDAO.registrarAcao(logErro);
            } catch (SQLException ex) {
                System.err.println("Erro ao registrar log de erro: " + ex.getMessage());
            }
            throw new Exception("Erro ao atualizar funcionario: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar a conexao: " + ex.getMessage());
                }
            }
        }
    }

    public void deletarFuncionario(String cpf, int funcionarioID) throws Exception {
        Connection conn = null;
        try {
            conn = ConexaoDAO.getConexao();
            conn.setAutoCommit(false);

            this.pessoaDAO = new PessoaDAO(conn);
            this.logDAO = new LogDAO(conn);
            this.funcionarioDAO = new FuncionarioDAO(conn);

            // 1. Validacao de campos
            if (cpf == null || cpf.isEmpty()) {
                throw new Exception("CPF eh um campo obrigatorio");
            }

            // 2. Deleta o funcionario
            funcionarioDAO.deletarFnc(funcionarioID);
            pessoaDAO.deletarPesCPF(cpf);

            // 3. Registra a acao no log
            LogVO log = new LogVO();
            log.setLog_acao("Funcionario deletado: " + cpf);
            log.setLog_fnc_id(funcionarioID);
            log.setLog_dataHora(java.time.LocalDateTime.now());
            logDAO.registrarAcao(log);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            LogVO logErro = new LogVO();
            logErro.setLog_acao("ERRO: " + e.getMessage());
            logErro.setLog_fnc_id(funcionarioID);
            logErro.setLog_dataHora(java.time.LocalDateTime.now());
            try {
                logDAO.registrarAcao(logErro);
            } catch (SQLException ex) {
                System.err.println("Erro ao registrar log de erro: " + ex.getMessage());
            }
            throw new Exception("Erro ao deletar funcionario: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar a conexao: " + ex.getMessage());
                }
            }
        }
    }

    // Metodo para buscar um funcionario por CPF
    public FuncionarioVO buscarFuncionarioPorCpf(String cpf) throws Exception {
        Connection conn = null;
        FuncionarioVO funcionario = null;
        try {
            conn = ConexaoDAO.getConexao();

            this.funcionarioDAO = new FuncionarioDAO(conn);

            if (cpf == null || cpf.isEmpty()) {
                throw new Exception("CPF eh um campo obrigatorio.");
            }

            funcionario = funcionarioDAO.buscarPorCpf(cpf);
            return funcionario;

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar funcionario: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar a conexao: " + ex.getMessage());
                }
            }
        }
    }

    // Metodo para buscar um funcionario por ID
    public FuncionarioVO buscarFuncionarioPorId(int id) throws Exception {
        Connection conn = null;
        FuncionarioVO funcionario = null;
        try {
            conn = ConexaoDAO.getConexao();

            this.funcionarioDAO = new FuncionarioDAO(conn);

            if (id <= 0) {
                throw new Exception("ID do funcionario eh invalido.");
            }

            funcionario = funcionarioDAO.buscarPorId(id);
            return funcionario;

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar funcionario: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar a conexao: " + ex.getMessage());
                }
            }
        }
    }

    // Metodo para buscar todos os funcionarios
    public List<FuncionarioVO> buscarTodosFuncionarios() throws Exception {
        Connection conn = null;
        List<FuncionarioVO> funcionarios = null;
        try {
            conn = ConexaoDAO.getConexao();

            this.funcionarioDAO = new FuncionarioDAO(conn);
            funcionarios = (List<FuncionarioVO>) funcionarioDAO.buscarPorCpf("%");

            return funcionarios;

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todos os funcionarios: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar a conexao: " + ex.getMessage());
                }
            }
        }
    }


}