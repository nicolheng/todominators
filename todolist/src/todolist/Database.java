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
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
            return false;
        }
    }

    public static boolean executeUpdate(String query, String title, String description, String dueDate, String category, int priorityID) {
        // Check if title is null or empty before proceeding
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Error: Task title cannot be empty.");
            return false;
        }
    
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Set the parameters for the prepared statement
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, dueDate);
            pstmt.setString(4, category);
            pstmt.setInt(5, priorityID);
            
            pstmt.executeUpdate(); // Executes the query
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
            return false;
        }
    }
    
}
