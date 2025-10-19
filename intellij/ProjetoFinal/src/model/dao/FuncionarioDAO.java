package model.dao;

import model.vo.FuncionarioVO;
import model.vo.PessoaVO;
import model.vo.CargoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private Connection con_fnc;
    private PessoaDAO pessoaDAO;
    private CargoDAO cargoDAO;

    public FuncionarioDAO(Connection con_fnc) {
        this.con_fnc = con_fnc;
        this.pessoaDAO = new PessoaDAO(con_fnc);
        this.cargoDAO = new CargoDAO(con_fnc);
    }

    // adicionar novo funcionario
    public int adicionarNovo(FuncionarioVO funcionario) throws SQLException {
        // salva os dados da pessoa
        pessoaDAO.adicionarNovo(funcionario);

        String sql = "INSERT INTO tb_funcionario (fnc_dtContratacao, fnc_dtDemissao, fnc_salario, fnc_cargo_id, fnc_pes_cpf) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement fnc_add = con_fnc.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fnc_add.setDate(1, new Date(funcionario.getFnc_dtContratacao().getTime()));
            fnc_add.setDate(2, null); // fnc_dtDemissao é nulo no cadastro inicial
            fnc_add.setDouble(3, funcionario.getFnc_salario());
            fnc_add.setInt(4, funcionario.getFnc_cargo().getCar_id());
            fnc_add.setString(5, funcionario.getPes_cpf());
            fnc_add.executeUpdate();

            try (ResultSet rs = fnc_add.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // update funcionario por id
    public void atualizar(FuncionarioVO funcionario) throws SQLException {
        // atualiza os dados da pessoa
        pessoaDAO.atualizar(funcionario);

        String sql = "UPDATE tb_funcionario SET fnc_dtContratacao = ?, fnc_dtDemissao = ?, fnc_salario = ?, fnc_cargo_id = ? WHERE fnc_id = ?";
        try (PreparedStatement fnc_att = con_fnc.prepareStatement(sql)) {
            fnc_att.setDate(1, new Date(funcionario.getFnc_dtContratacao().getTime()));
            fnc_att.setDate(2, new Date(funcionario.getFnc_dtDemissao().getTime()));
            fnc_att.setDouble(3, funcionario.getFnc_salario());
            fnc_att.setInt(4, funcionario.getFnc_cargo().getCar_id());
            fnc_att.setInt(5, funcionario.getFnc_id());
            fnc_att.executeUpdate();
        }
    }

    // update funcionario por cpf
    public void atualizarPorCpf(FuncionarioVO funcionario) throws SQLException {
        // Primeiro, atualiza os dados da pessoa
        pessoaDAO.atualizar(funcionario);

        String sql = "UPDATE tb_funcionario SET fnc_dtContratacao = ?, fnc_dtDemissao = ?, fnc_salario = ?, fnc_cargo_id = ? WHERE fnc_pes_cpf = ?";
        try (PreparedStatement fnc_att = con_fnc.prepareStatement(sql)) {
            fnc_att.setDate(1, new Date(funcionario.getFnc_dtContratacao().getTime()));
            fnc_att.setDate(2, new Date(funcionario.getFnc_dtDemissao().getTime()));
            fnc_att.setDouble(3, funcionario.getFnc_salario());
            fnc_att.setInt(4, funcionario.getFnc_cargo().getCar_id());
            fnc_att.setString(5, funcionario.getPes_cpf());
            fnc_att.executeUpdate();
        }
    }

    // busca funcionario por id
    public FuncionarioVO buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_funcionario WHERE fnc_id = ?";
        FuncionarioVO funcionario = null;
        try (PreparedStatement fnc_bsc = con_fnc.prepareStatement(sql)) {
            fnc_bsc.setInt(1, id);
            try (ResultSet rs = fnc_bsc.executeQuery()) {
                if (rs.next()) {
                    PessoaVO pessoa = pessoaDAO.buscarPorCpf(rs.getString("fnc_pes_cpf"));
                    CargoVO cargo = cargoDAO.buscarPorId(rs.getInt("fnc_cargo_id"));
                    funcionario = new FuncionarioVO(
                            pessoa.getPes_cpf(),
                            pessoa.getPes_nome(),
                            pessoa.getPes_sexo(),
                            pessoa.getPes_dt_nascimento(),
                            pessoa.getPes_email(),
                            pessoa.getTelefone(),
                            pessoa.getEndereco(),
                            rs.getInt("fnc_id"),
                            rs.getDate("fnc_dtContratacao"),
                            rs.getDate("fnc_dtDemissao"),
                            rs.getDouble("fnc_salario"),
                            cargo,
                            pessoa
                    );
                }
            }
        }
        return funcionario;
    }

    // busca funcionario por cpf
    public FuncionarioVO buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM tb_funcionario WHERE fnc_pes_cpf = ?";
        FuncionarioVO funcionario = null;
        try (PreparedStatement fnc_bsc = con_fnc.prepareStatement(sql)) {
            fnc_bsc.setString(1, cpf);
            try (ResultSet rs = fnc_bsc.executeQuery()) {
                if (rs.next()) {
                    PessoaVO pessoa = pessoaDAO.buscarPorCpf(rs.getString("fnc_pes_cpf"));
                    CargoVO cargo = cargoDAO.buscarPorId(rs.getInt("fnc_cargo_id"));
                    funcionario = new FuncionarioVO(
                            pessoa.getPes_cpf(),
                            pessoa.getPes_nome(),
                            pessoa.getPes_sexo(),
                            pessoa.getPes_dt_nascimento(),
                            pessoa.getPes_email(),
                            pessoa.getTelefone(),
                            pessoa.getEndereco(),
                            rs.getInt("fnc_id"),
                            rs.getDate("fnc_dtContratacao"),
                            rs.getDate("fnc_dtDemissao"),
                            rs.getDouble("fnc_salario"),
                            cargo,
                            pessoa
                    );
                }
            }
        }
        return funcionario;
    }

    public void deletarFnc(int id) throws SQLException {
        // 1. Encontra o CPF do funcionário antes de deletar
        FuncionarioVO funcionario = buscarPorId(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }
        String cpfPessoa = funcionario.getPes_cpf();

        try {
            con_fnc.setAutoCommit(false);

            // deleta o funcionário da tabela tb_funcionario
            String sqlFuncionario = "DELETE FROM tb_funcionario WHERE fnc_id = ?";
            try (PreparedStatement stmtFuncionario = con_fnc.prepareStatement(sqlFuncionario)) {
                stmtFuncionario.setInt(1, id);
                stmtFuncionario.executeUpdate();
            }

            // 3 deleta a pessoa da tabela tb_pessoa
            pessoaDAO.deletarCPF(cpfPessoa);

            // confirma a transação
            con_fnc.commit();
            System.out.println("Funcionário e dados da pessoa deletados com sucesso.");

        } catch (SQLException e) {
            con_fnc.rollback();
            throw e;
        } finally {
            // Restaura o modo de autocommit
            con_fnc.setAutoCommit(true);
        }
    }

    //deletar funcionario por cpf
    public void deletarFncCpf(String cpf) throws SQLException {
        String sql = "DELETE FROM tb_funcionario WHERE pes_cpf = ?";
        try (PreparedStatement fnc_del_cpf = fnc_del_cpf.prepareStatement(sql)) {
            fnc_del_cpf.setString(1, cpf);
            fnc_del_cpf.executeUpdate();
        }
    }
}
