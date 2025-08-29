import java.sql.Connection;
import java.sql.SQLException;
import model.dao.ConexaoDAO;

public class teste {
    public static void main(String[] args) {

        try{
            Connection connection = ConexaoDAO.getConexao();

            if (connection != null){
                System.out.println("Conexao feita!");
                connection.close();
            }
        } catch (SQLException e){
            System.err.println("Erro ao conectar");
            e.printStackTrace(); // rastreio de erro
        }
    }
}
