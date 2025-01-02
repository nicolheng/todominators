package todolist;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static boolean executeUpdate(String query, String title, String description, String dueDate, String category, int priorityID, int recurringID) {
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
            pstmt.setInt(6, recurringID);
            
            pstmt.executeUpdate(); // Executes the query
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
            return false;
        }
    }

    public static ObservableList<Task> getTasksFromDatabase() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        String query = "SELECT task_name, task_description, task_due_date, task_category, task_priorityID, task_recurringID FROM tasks";

        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            while (rs.next()) {
                String name = rs.getString("task_name");
                String description = rs.getString("task_description");
                String dueDateString = rs.getString("task_due_date");
                LocalDate dueDate = LocalDate.parse(dueDateString, dateFormatter);
                String category = rs.getString("task_category");
                int priorityID = rs.getInt("task_priorityID");
                int recurringID = rs.getInt("task_recurringID");

                // Create Task object and add to the list
                Task task = new Task(name, description, dueDate, category, priorityID, recurringID);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
        return tasks;
}

    
}
