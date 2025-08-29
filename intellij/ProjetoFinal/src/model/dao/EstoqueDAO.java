/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package model.dao;

import model.vo.EstoqueVO;
import model.vo.FornecedorVO;
import model.vo.ProdutoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstoqueDAO {
    private Connection con_estoque;
    private ProdutoDAO produtoDAO;
    private FornecedorDAO fornecedorDAO;

    public EstoqueDAO(Connection con_estoque) {
        this.con_estoque = con_estoque;
        this.produtoDAO = new ProdutoDAO(con_estoque);
        this.fornecedorDAO = new FornecedorDAO(con_estoque);
    }

    // adicionar novo estoque
    public int adicionarNovo(EstoqueVO estoque) throws SQLException {
        String sql = "INSERT INTO tb_estoque (est_dtCompra, est_produto_id, est_custo, est_qtdToal, est_lote, est_dtValidade, est_forn_cnpj) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement estoque_add = con_estoque.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            estoque_add.setDate(1, new java.sql.Date(estoque.getEst_dtCompra().getTime()));
            estoque_add.setInt(2, estoque.getEst_produto_id().getProduto_id());
            estoque_add.setDouble(3, estoque.getEst_custo());
            estoque_add.setDouble(4, estoque.getQtdTotal());
            estoque_add.setString(5, estoque.getEst_lote());
            estoque_add.setDate(6, new java.sql.Date(estoque.getEst_dtValidade().getTime()));
            estoque_add.setString(7, estoque.getEst_forn_cnpj().getForn_cnpj());
            estoque_add.executeUpdate();
            try (ResultSet rs = estoque_add.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // update estoque por id
    public void atualizarPorId(EstoqueVO estoque) throws SQLException {
        String sql = "UPDATE tb_estoque SET est_dtCompra = ?, est_produto_id = ?, est_custo = ?, est_qtdToal = ?, est_lote = ?, est_dtValidade = ?, est_forn_cnpj = ? WHERE est_id = ?";
        try (PreparedStatement estoque_att_id = con_estoque.prepareStatement(sql)) {
            estoque_att_id.setDate(1, new java.sql.Date(estoque.getEst_dtCompra().getTime()));
            estoque_att_id.setInt(2, estoque.getEst_produto_id().getProduto_id());
            estoque_att_id.setDouble(3, estoque.getEst_custo());
            estoque_att_id.setDouble(4, estoque.getQtdTotal());
            estoque_att_id.setString(5, estoque.getEst_lote());
            estoque_att_id.setDate(6, new java.sql.Date(estoque.getEst_dtValidade().getTime()));
            estoque_att_id.setString(7, estoque.getEst_forn_cnpj().getForn_cnpj());
            estoque_att_id.setInt(8, estoque.getEst_id());
            estoque_att_id.executeUpdate();
        }
    }

    // update estoque por lote
    public void atualizarPorLote(String loteAntigo, EstoqueVO estoqueNovo) throws SQLException {
        String sql = "UPDATE tb_estoque SET est_dtCompra = ?, est_produto_id = ?, est_custo = ?, est_qtdToal = ?, est_lote = ?, est_dtValidade = ?, est_forn_cnpj = ? WHERE est_lote = ?";
        try (PreparedStatement estoque_att_lote = con_estoque.prepareStatement(sql)) {
            estoque_att_lote.setDate(1, new java.sql.Date(estoqueNovo.getEst_dtCompra().getTime()));
            estoque_att_lote.setInt(2, estoqueNovo.getEst_produto_id().getProduto_id());
            estoque_att_lote.setDouble(3, estoqueNovo.getEst_custo());
            estoque_att_lote.setDouble(4, estoqueNovo.getQtdTotal());
            estoque_att_lote.setString(5, estoqueNovo.getEst_lote());
            estoque_att_lote.setDate(6, new java.sql.Date(estoqueNovo.getEst_dtValidade().getTime()));
            estoque_att_lote.setString(7, estoqueNovo.getEst_forn_cnpj().getForn_cnpj());
            estoque_att_lote.setString(8, loteAntigo);
            estoque_att_lote.executeUpdate();
        }
    }

    // busca estoque por id
    public EstoqueVO buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_estoque WHERE est_id = ?";
        EstoqueVO estoque = null;
        try (PreparedStatement estoque_bsc_id = con_estoque.prepareStatement(sql)) {
            estoque_bsc_id.setInt(1, id);
            try (ResultSet rs = estoque_bsc_id.executeQuery()) {
                if (rs.next()) {
                    estoque = new EstoqueVO();
                    estoque.setEst_id(rs.getInt("est_id"));
                    estoque.setEst_dtCompra(rs.getDate("est_dtCompra"));
                    estoque.setEst_custo(rs.getDouble("est_custo"));
                    estoque.setQtdTotal(rs.getDouble("est_qtdToal"));
                    estoque.setEst_lote(rs.getString("est_lote"));
                    estoque.setEst_dtValidade(rs.getDate("est_dtValidade"));

                    // Buscando objetos relacionados
                    ProdutoVO produto = produtoDAO.buscarPorId(rs.getInt("est_produto_id"));
                    estoque.setEst_produto_id(produto);

                    FornecedorVO fornecedor = fornecedorDAO.buscarPorCnpj(rs.getString("est_forn_cnpj"));
                    estoque.setEst_forn_cnpj(fornecedor);
                }
            }
        }
        return estoque;
    }

    // busca estoque por lote
    public EstoqueVO buscarPorLote(String lote) throws SQLException {
        String sql = "SELECT * FROM tb_estoque WHERE est_lote = ?";
        EstoqueVO estoque = null;
        try (PreparedStatement estoque_bsc_lote = con_estoque.prepareStatement(sql)) {
            estoque_bsc_lote.setString(1, lote);
            try (ResultSet rs = estoque_bsc_lote.executeQuery()) {
                if (rs.next()) {
                    estoque = new EstoqueVO();
                    estoque.setEst_id(rs.getInt("est_id"));
                    estoque.setEst_dtCompra(rs.getDate("est_dtCompra"));
                    estoque.setEst_custo(rs.getDouble("est_custo"));
                    estoque.setQtdTotal(rs.getDouble("est_qtdToal"));
                    estoque.setEst_lote(rs.getString("est_lote"));
                    estoque.setEst_dtValidade(rs.getDate("est_dtValidade"));

                    // Buscando objetos relacionados
                    ProdutoVO produto = produtoDAO.buscarPorId(rs.getInt("est_produto_id"));
                    estoque.setEst_produto_id(produto);

                    FornecedorVO fornecedor = fornecedorDAO.buscarPorCnpj(rs.getString("est_forn_cnpj"));
                    estoque.setEst_forn_cnpj(fornecedor);
                }
            }
        }
        return estoque;
    }
}
