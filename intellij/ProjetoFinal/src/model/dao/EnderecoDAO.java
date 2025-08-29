/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package model.dao;

import model.vo.BairroVO;
import model.vo.CidadeVO;
import model.vo.EnderecoVO;
import model.vo.EstadoVO;
import model.vo.LogradouroVO;
import model.vo.EndPostalVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EnderecoDAO {
    private Connection con_endp;
    private LogradouroDAO logradouroDAO;
    private BairroDAO bairroDAO;
    private CidadeDAO cidadeDAO;
    private EstadoDAO estadoDAO;

    public EnderecoDAO(Connection con_endp) {
        this.con_endp = con_endp;
        try {
            this.logradouroDAO = new LogradouroDAO(con_endp);
            this.bairroDAO = new BairroDAO(con_endp);
            this.cidadeDAO = new CidadeDAO(con_endp);
            this.estadoDAO = new EstadoDAO(con_endp);
        } catch (SQLException e) {
            System.err.println("Erro ao instanciar DAOs auxiliares em EnderecoDAO: " + e.getMessage());
        }
    }

    // adicionar novo endereco
    public int adicionarNovo(EnderecoVO endereco) throws SQLException {
        String sql = "INSERT INTO tb_endereco (end_endP_id, end_numero, end_complemento) VALUES (?, ?, ?)";
        try (PreparedStatement end_add = con_endp.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            end_add.setInt(1, endereco.getEnd_endP_id().getEndP_id());
            end_add.setString(2, endereco.getEnd_numero());
            end_add.setString(3, endereco.getEnd_complemento());
            end_add.executeUpdate();
            try (ResultSet rs = end_add.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // buscar endereco por id
    public EnderecoVO buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_endereco WHERE end_id = ?";
        EnderecoVO endereco = null;
        try (PreparedStatement end_bsc_id = con_endp.prepareStatement(sql)) {
            end_bsc_id.setInt(1, id);
            try (ResultSet rs = end_bsc_id.executeQuery()) {
                if (rs.next()) {
                    endereco = new EnderecoVO();
                    endereco.setEnd_id(rs.getInt("end_id"));
                    endereco.setEnd_numero(rs.getString("end_numero"));
                    endereco.setEnd_complemento(rs.getString("end_complemento"));

                    EndPostalDAO endPostalDAO = new EndPostalDAO(con_endp);
                    EndPostalVO endPostal = endPostalDAO.buscarPorId(rs.getInt("end_endP_id"));
                    endereco.setEnd_endP_id(endPostal);
                }
            }
        }
        return endereco;
    }
}
