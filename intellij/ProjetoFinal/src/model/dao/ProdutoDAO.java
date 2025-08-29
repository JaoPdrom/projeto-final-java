/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package model.dao;

import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoDAO {
    private Connection con_produto;

    public ProdutoDAO(Connection con_produto) {
        this.con_produto = con_produto;
    }

    // adicionar novo
    public int adicionarNovo(ProdutoVO produto) throws SQLException {
        String sql = "INSERT INTO tb_produto (produto_nome, produto_qtdMax, produto_qtdMin, produto_tipoPdt) VALUES (?, ?, ?, ?)";
        try (PreparedStatement produto_add = con_produto.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            produto_add.setString(1, produto.getProduto_nome());
            produto_add.setDouble(2, produto.getProduto_qtdMax());
            produto_add.setDouble(3, produto.getProduto_qtdMin());
            produto_add.setInt(4, produto.getProduto_tipoPdt().getTipoPdt_id());
            produto_add.executeUpdate();
            try (ResultSet rs = produto_add.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // update produto por id
    public void atualizarPorId(ProdutoVO produto) throws SQLException {
        String sql = "UPDATE tb_produto SET produto_nome = ?, produto_qtdMax = ?, produto_qtdMin = ?, produto_tipoPdt = ? WHERE produto_id = ?";
        try (PreparedStatement produto_att_id = con_produto.prepareStatement(sql)) {
            produto_att_id.setString(1, produto.getProduto_nome());
            produto_att_id.setDouble(2, produto.getProduto_qtdMax());
            produto_att_id.setDouble(3, produto.getProduto_qtdMin());
            produto_att_id.setInt(4, produto.getProduto_tipoPdt().getTipoPdt_id());
            produto_att_id.setInt(5, produto.getProduto_id());
            produto_att_id.executeUpdate();
        }
    }

    // update produto por nome
    public void atualizarPorNome(String nomeAntigo, ProdutoVO produtoNovo) throws SQLException {
        String sql = "UPDATE tb_produto SET produto_nome = ?, produto_qtdMax = ?, produto_qtdMin = ?, produto_tipoPdt = ? WHERE produto_nome = ?";
        try (PreparedStatement produto_att_nome = con_produto.prepareStatement(sql)) {
            produto_att_nome.setString(1, produtoNovo.getProduto_nome());
            produto_att_nome.setDouble(2, produtoNovo.getProduto_qtdMax());
            produto_att_nome.setDouble(3, produtoNovo.getProduto_qtdMin());
            produto_att_nome.setInt(4, produtoNovo.getProduto_tipoPdt().getTipoPdt_id());
            produto_att_nome.setString(5, nomeAntigo);
            produto_att_nome.executeUpdate();
        }
    }

    // busca produto por id
    public ProdutoVO buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_produto WHERE produto_id = ?";
        ProdutoVO produto = null;
        try (PreparedStatement produto_bsc_id = con_produto.prepareStatement(sql)) {
            produto_bsc_id.setInt(1, id);
            try (ResultSet rs = produto_bsc_id.executeQuery()) {
                if (rs.next()) {
                    produto = new ProdutoVO();
                    produto.setProduto_id(rs.getInt("produto_id"));
                    produto.setProduto_nome(rs.getString("produto_nome"));
                    produto.setProduto_qtdMax(rs.getDouble("produto_qtdMax"));
                    produto.setProduto_qtdMin(rs.getDouble("produto_qtdMin"));

                    // Buscando o Tipo de Produto
                    TipoProdutoDAO tipoPdtDAO = new TipoProdutoDAO(con_produto);
                    TipoProdutoVO tipoProduto = tipoPdtDAO.buscarPorId(rs.getInt("produto_tipoPdt"));
                    produto.setProduto_tipoPdt(tipoProduto);
                }
            }
        }
        return produto;
    }

    // busca produto por nome
    public ProdutoVO buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM tb_produto WHERE produto_nome = ?";
        ProdutoVO produto = null;
        try (PreparedStatement produto_bsc_nome = con_produto.prepareStatement(sql)) {
            produto_bsc_nome.setString(1, nome);
            try (ResultSet rs = produto_bsc_nome.executeQuery()) {
                if (rs.next()) {
                    produto = new ProdutoVO();
                    produto.setProduto_id(rs.getInt("produto_id"));
                    produto.setProduto_nome(rs.getString("produto_nome"));
                    produto.setProduto_qtdMax(rs.getDouble("produto_qtdMax"));
                    produto.setProduto_qtdMin(rs.getDouble("produto_qtdMin"));

                    // Buscando o Tipo de Produto
                    TipoProdutoDAO tipoPdtDAO = new TipoProdutoDAO(con_produto);
                    TipoProdutoVO tipoProduto = tipoPdtDAO.buscarPorId(rs.getInt("produto_tipoPdt"));
                    produto.setProduto_tipoPdt(tipoProduto);
                }
            }
        }
        return produto;
    }
}
