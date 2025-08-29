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

// ClienteDAO herda de PessoaDAO para reutilizar a lógica de persistência de Pessoa
public class ClienteDAO extends PessoaDAO {

    public ClienteDAO(Connection con_cliente) {
        // Chama o construtor da classe pai (PessoaDAO)
        super(con_cliente);
    }

    // Este método irá salvar os dados do cliente na tabela tb_pessoa
    // Ele aproveita o método 'adicionarNovo' da classe PessoaDAO
    public void adicionarNovo(PessoaVO cliente) throws SQLException {
        super.adicionarNovo(cliente);
    }

    // Este método irá atualizar os dados do cliente na tabela tb_pessoa
    // Ele aproveita o método 'atualizar' da classe PessoaDAO
    public void atualizar(PessoaVO cliente) throws SQLException {
        super.atualizar(cliente);
    }

    // Os métodos de busca (por CPF e por nome) também são herdados de PessoaDAO
    // e podem ser usados diretamente
}
