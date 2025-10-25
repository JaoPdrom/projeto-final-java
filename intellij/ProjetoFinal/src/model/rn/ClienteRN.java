/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package model.rn;

import model.dao.ClienteDAO;
import model.dao.ConexaoDAO;
import model.vo.ClienteVO;
import model.vo.PessoaVO;

import java.sql.Connection;
import java.sql.SQLException;

public class ClienteRN {
//    public ClienteRN() throws SQLException {
//    }
//
//    private PessoaRN pessoaRN = new PessoaRN();
//    private ClienteDAO clienteDAO = new ClienteDAO();
//
//
//    //cadastro de um novo cliente
//    public void adicionarCliente(PessoaVO pessoa, ClienteVO cliente) throws Exception {
//        Connection con = ConexaoDAO.getConexao();
//        try {
//            con.setAutoCommit(false);
//
//            // 1) Garante que a pessoa existe
//            PessoaVO existente = pessoaRN.buscarPorCpfRN(pessoa.getPes_cpf());
//            if (existente == null) {
//                pessoaRN.adicionarPessoa(pessoa);
//            }
//
//            // 2) Adiciona dados específicos do cliente
//            clienteDAO.adicionarNovoCliente(cliente);
//
//            con.commit();
//        } catch (Exception e) {
//            con.rollback();
//            throw new Exception("Erro ao cadastrar cliente: " + e.getMessage(), e);
//        } finally {
//            con.close();
//        }
//    }

}
