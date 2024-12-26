package todolist;
import java.sql.*;

public class Database {
    private static String url = "jdbc:sqlite:tasks.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static boolean executeUpdate(String query){
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement(); ) {
            stmt.executeUpdate(query); // Executes the query (e.g., INSERT, UPDATE, DELETE)
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
            return false;
        }
    }

}
