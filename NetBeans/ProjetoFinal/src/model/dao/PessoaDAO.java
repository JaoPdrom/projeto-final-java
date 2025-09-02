package model.dao;

import model.vo.PessoaVO;
import model.vo.SexoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    private Connection con_pessoa;
    private SexoDAO sexoDAO;

    public PessoaDAO(Connection con_pessoa) {
        this.con_pessoa = con_pessoa;
        this.sexoDAO = new SexoDAO(con_pessoa);
    }

    // adicionar nova pessoa
    public void adicionarNovo(PessoaVO pessoa) throws SQLException {
        String sql = "INSERT INTO tb_pessoa (pes_cpf, pes_nome, pes_sex_id, pes_dtNascimento, pes_email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pessoa_add = con_pessoa.prepareStatement(sql)) {
            pessoa_add.setString(1, pessoa.getPes_cpf());
            pessoa_add.setString(2, pessoa.getPes_nome());
            pessoa_add.setInt(3, pessoa.getPes_sexo().getSex_id());
            pessoa_add.setDate(4, new Date(pessoa.getPes_dt_nascimento().getTime()));
            pessoa_add.setString(5, pessoa.getPes_email());
            pessoa_add.executeUpdate();
        }
    }

    // update pessoa por cpf
    public void atualizar(PessoaVO pessoa) throws SQLException {
        String sql = "UPDATE tb_pessoa SET pes_nome = ?, pes_sex_id = ?, pes_dtNascimento = ?, pes_email = ? WHERE pes_cpf = ?";
        try (PreparedStatement pessoa_att = con_pessoa.prepareStatement(sql)) {
            pessoa_att.setString(1, pessoa.getPes_nome());
            pessoa_att.setInt(2, pessoa.getPes_sexo().getSex_id());
            pessoa_att.setDate(3, new Date(pessoa.getPes_dt_nascimento().getTime()));
            pessoa_att.setString(4, pessoa.getPes_email());
            pessoa_att.setString(5, pessoa.getPes_cpf());
            pessoa_att.executeUpdate();
        }
    }

    // busca pessoa por cpf
    public PessoaVO buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM tb_pessoa WHERE pes_cpf = ?";
        PessoaVO pessoa = null;
        try (PreparedStatement pessoa_bsc = con_pessoa.prepareStatement(sql)) {
            pessoa_bsc.setString(1, cpf);
            try (ResultSet rs = pessoa_bsc.executeQuery()) {
                if (rs.next()) {
                    pessoa = new PessoaVO();
                    pessoa.setPes_cpf(rs.getString("pes_cpf"));
                    pessoa.setPes_nome(rs.getString("pes_nome"));
                    pessoa.setPes_sexo(sexoDAO.buscarPorId(rs.getInt("pes_sex_id")));
                    pessoa.setPes_dt_nascimento(rs.getDate("pes_dtNascimento"));
                    pessoa.setPes_email(rs.getString("pes_email"));
                }
            }
        }
        return pessoa;
    }

    // busca pessoa por nome
    public List<PessoaVO> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM tb_pessoa WHERE pes_nome LIKE ?";
        List<PessoaVO> pessoas = new ArrayList<>();
        try (PreparedStatement pessoa_bsc = con_pessoa.prepareStatement(sql)) {
            pessoa_bsc.setString(1, "%" + nome + "%");
            try (ResultSet rs = pessoa_bsc.executeQuery()) {
                while (rs.next()) {
                    PessoaVO pessoa = new PessoaVO();
                    pessoa.setPes_cpf(rs.getString("pes_cpf"));
                    pessoa.setPes_nome(rs.getString("pes_nome"));
                    pessoa.setPes_sexo(sexoDAO.buscarPorId(rs.getInt("pes_sex_id")));
                    pessoa.setPes_dt_nascimento(rs.getDate("pes_dtNascimento"));
                    pessoa.setPes_email(rs.getString("pes_email"));
                    pessoas.add(pessoa);
                }
            }
        }
        return pessoas;
    }

    // deletar pessoa
    public void deletar(PessoaVO pessoa) throws SQLException {
        String sql = "DELETE FROM tb_pessoa WHERE pes_cpf = ?";
        try (PreparedStatement pessoa_del = con_pessoa.prepareStatement(sql)) {
            pessoa_del.setString(1, pessoa.getPes_cpf());
            pessoa_del.executeUpdate();
        }
    }
}
