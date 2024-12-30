
package todolistgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import javafx.concurrent.Task;
/**
 *
 * @author synye
 */
public class Database1 {
    private static final String DB_URL = "jdbc:sqlite:/C:/Users/User/Downloads/tasks.db"; // SQLite DB file

    private String taskName;
    private int taskId;
    private ArrayList<String> taskDependsName;
    private ArrayList<Integer> taskDependsId;
    private ArrayList<Boolean> isComplete;

    
    public Database1() {
        taskName = null;
        taskId = 0;
        taskDependsName = new ArrayList<>();
        taskDependsId = new ArrayList<>();
        isComplete = new ArrayList<>();
    }
    
    // Connect to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL); // Establish connection to SQLite DB
    }
/*
    // Method to execute a query (insert, update, delete)
    public static boolean executeUpdate(String query, String title, String description, String toString, String category, String priority){
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(query); // Executes the query (e.g., INSERT, UPDATE, DELETE)
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
            return false;
        }
    }
*/
    public static boolean executeUpdate(String query, String title, String description, String dueDate, String category, String priority) {
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
        pstmt.setString(5, priority);
        
        pstmt.executeUpdate(); // Executes the query
        return true;
    } catch (SQLException e) {
        System.out.println("Error occurs: " + e.getMessage());
        return false;
    }
}

// Method to execute a SELECT query
    public static void executeQuery(String query) {
        try (Connection conn = getConnection(); 
            Statement stmt = conn.createStatement();){
            
            ResultSet rs = stmt.executeQuery(query);
            char taskLetter = 'A';
            int taskNumber = 1;
            while (rs.next()) {
                String title = rs.getString("task_name");
                String dueDate = rs.getString("task_due_date");
                boolean isCompleted = rs.getBoolean("is_completed");

                // Display the task
                System.out.println(taskNumber+". ["+(isCompleted ? "Completed" : "Not Completed")+"] Task " + taskLetter + ": "+title+" - Due: "+dueDate);

                // Move to the next task label and increment task number
                taskLetter++;
                taskNumber++;
            }
            
            rs.close();
            stmt.close();
            conn.close();
   
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
    }
    
// Method to fetch a task by its task_id
public static task getTaskById(int taskId) {
    String query = "SELECT * FROM tasks WHERE task_id = ?";
    task task = null;

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        // Set the task_id parameter
        pstmt.setInt(1, taskId);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                // Create a new Task object from the result set
                task = new task(
                    rs.getInt("task_id"),               // task_id
                    rs.getString("task_name"),          // task_name
                    rs.getString("task_description"),   // task_description
                    rs.getString("task_due_date"),      // task_due_date
                    rs.getString("category"),           // category
                    rs.getString("priority"),           // priority
                    rs.getBoolean("is_completed")       // is_completed
                );
            }
        }

    } catch (SQLException e) {
        System.out.println("Error fetching task: " + e.getMessage());
    }

    return task;  // Return the Task object (null if no task found)
}

    // Method to execute a select query
    public void executeDependencyCheck(String query) {
        try (Connection conn = getConnection(); 
            Statement stmt = conn.createStatement();){
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                taskName = rs.getString("task_name");
                taskId = rs.getInt("task_id");
                taskDependsName.add(rs.getString("task_name"));
                taskDependsId.add(rs.getInt("depends_on_task_id"));
                isComplete.add(rs.getBoolean("is_completed"));
    
            }
            
            rs.close();
   
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
    }
    
    public static String executeName(String query) {
        try (Connection conn = getConnection(); 
            Statement stmt = conn.createStatement();){
            
            ResultSet rs = stmt.executeQuery(query);
            
            String name = rs.getString("task_name");
            
            rs.close();
            return name;
   
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
        return null;
    }
}