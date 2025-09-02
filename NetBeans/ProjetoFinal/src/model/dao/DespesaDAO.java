package model.dao;

import model.vo.DespesaVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class DespesaDAO {
    private Connection con_despesa;

    public DespesaDAO(Connection con_despesa) {
        this.con_despesa = con_despesa;
    }

    // Adicionar nova despesa
    public int adicionarNovo(DespesaVO despesa) throws SQLException {
        String sql = "INSERT INTO td_despesa (despesa_descricao, despesa_dtRealiazacao, despesa_valor_pago) VALUES (?, ?, ?)";
        try (PreparedStatement despesa_add = con_despesa.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            despesa_add.setString(1, despesa.getDespesa_descricao());
            despesa_add.setDate(2, new Date(despesa.getDespesa_dtRealizacao().getTime()));
            despesa_add.setDouble(3, despesa.getDespesa_valor_pago());
            despesa_add.executeUpdate();
            try (ResultSet rs = despesa_add.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // Update despesa por id
    public void atualizarPorId(DespesaVO despesa) throws SQLException {
        String sql = "UPDATE td_despesa SET despesa_descricao = ?, despesa_dtRealiazacao = ?, despesa_valor_pago = ? WHERE despesa_id = ?";
        try (PreparedStatement despesa_att_id = con_despesa.prepareStatement(sql)) {
            despesa_att_id.setString(1, despesa.getDespesa_descricao());
            despesa_att_id.setDate(2, new Date(despesa.getDespesa_dtRealizacao().getTime()));
            despesa_att_id.setDouble(3, despesa.getDespesa_valor_pago());
            despesa_att_id.setInt(4, despesa.getDespesa_id());
            despesa_att_id.executeUpdate();
        }
    }

    // Update despesa por descricao
    public void atualizarPorDescricao(String descricaoAntiga, String descricaoNova) throws SQLException {
        String sql = "UPDATE td_despesa SET despesa_descricao = ? WHERE despesa_descricao = ?";
        try (PreparedStatement despesa_att_desc = con_despesa.prepareStatement(sql)) {
            despesa_att_desc.setString(1, descricaoNova);
            despesa_att_desc.setString(2, descricaoAntiga);
            despesa_att_desc.executeUpdate();
        }
    }

    // Busca despesa por id
    public DespesaVO buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM td_despesa WHERE despesa_id = ?";
        DespesaVO despesa = null;
        try (PreparedStatement despesa_bsc_id = con_despesa.prepareStatement(sql)) {
            despesa_bsc_id.setInt(1, id);
            try (ResultSet rs = despesa_bsc_id.executeQuery()) {
                if (rs.next()) {
                    despesa = new DespesaVO();
                    despesa.setDespesa_id(rs.getInt("despesa_id"));
                    despesa.setDespesa_descricao(rs.getString("despesa_descricao"));
                    despesa.setDespesa_dtRealizacao(rs.getDate("despesa_dtRealiazacao"));
                    despesa.setDespesa_valor_pago(rs.getDouble("despesa_valor_pago"));
                }
            }
        }
        return despesa;
    }

    // Busca despesa por descricao
    public DespesaVO buscarPorDescricao(String descricao) throws SQLException {
        String sql = "SELECT * FROM td_despesa WHERE despesa_descricao = ?";
        DespesaVO despesa = null;
        try (PreparedStatement despesa_bsc_desc = con_despesa.prepareStatement(sql)) {
            despesa_bsc_desc.setString(1, descricao);
            try (ResultSet rs = despesa_bsc_desc.executeQuery()) {
                if (rs.next()) {
                    despesa = new DespesaVO();
                    despesa.setDespesa_id(rs.getInt("despesa_id"));
                    despesa.setDespesa_descricao(rs.getString("despesa_descricao"));
                    despesa.setDespesa_dtRealizacao(rs.getDate("despesa_dtRealiazacao"));
                    despesa.setDespesa_valor_pago(rs.getDouble("despesa_valor_pago"));
                }
            }
        }
        return despesa;
    }
}
