package connessione;
import java.sql.*;

public class Connection {

    public Connection() throws SQLException{
        host = "localhost";
        port = "3306";
        db = "lapalmedor";
        user = "root";
        pass = "cosmo123";
        driver = "com.mysql.cj.jdbc.Driver";
    }

    public void createConnection() {
        try {
            Class.forName(driver);
            String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
            con = DriverManager.getConnection(url, user, pass);
            statement = con.createStatement();
            System.out.println("Connessione OK");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
 
    public void executeNoResultQuery (String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet excecuteResultQuery (String query) {
        ResultSet result = null;

        try {
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void closeConnection(){
        try {
            if(!con.isClosed())
                con.close();
            System.out.println("connessione chiusa");

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static java.sql.Connection con;
    private static String host;
    private static String port;
    private static String db;
    private static String user;
    private static String pass;
    private static String driver;
    private Statement statement;
}