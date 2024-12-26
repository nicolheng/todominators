/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package todolist;

import java.sql.*;
import java.util.*;

/**
 *
 * @author synye
 */
public class DatabaseOld {
    private static final String DB_URL = "jdbc:sqlite:/assignment/task.db"; // SQLite DB file
    private ArrayList<String> t_taskName, t_taskDescription, t_taskDueDate, t_taskCategory, t_taskPriority, taskDependsName;
    private ArrayList<Integer> t_taskId, td_taskID, td_taskDependsId, taskId, taskDependsId;
    private ArrayList<Boolean> t_isCompleted, isCompleted;

    private Map<Integer, Integer> dependencyMap;
    
    public Database() {
        t_taskName = new ArrayList<>(); 
        t_taskDescription = new ArrayList<>();
        t_taskDueDate = new ArrayList<>();
        t_taskCategory = new ArrayList<>();
        t_taskPriority = new ArrayList<>();
        t_taskId = new ArrayList<>();
        t_isCompleted = new ArrayList<>();
        
        td_taskID = new ArrayList<>();
        td_taskDependsId = new ArrayList<>();
        
        taskDependsName = new ArrayList<>();
        taskId = new ArrayList<>();
        taskDependsId = new ArrayList<>();
        isCompleted = new ArrayList<>();
    }
    
    // Connect to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL); // Establish connection to SQLite DB
    }

    // Method to execute a query (insert, update, delete)
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

    public void executeTaskLoad() {
        String query = "SELECT * FROM tasks";
    
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);) {

            // Check if there are tasks
            while (rs.next()) {
                t_taskId.add(rs.getInt("task_id"));
                t_taskName.add(rs.getString("task_name"));
                t_taskDescription.add(rs.getString("task_description"));
                t_taskDueDate.add(rs.getString("task_due_date"));
                t_taskCategory.add(rs.getString("task_category"));
                t_taskPriority.add(rs.getString("task_priority"));
                t_isCompleted.add(rs.getBoolean("is_completed"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
        }
    }


    public boolean executeCompleteCheck(String query, String n) {
        try (Connection conn = getConnection(); 
            Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);){
            
            while (rs.next()) {
                taskDependsName.add(rs.getString("task_name"));
                taskDependsId.add(rs.getInt("depends_on_task_id"));
                isCompleted.add(rs.getBoolean("is_completed"));
    
            }
            
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
        
        boolean dependencyDone = true;
        for (int i = 0; i < isCompleted.size(); i++)
            if (!isCompleted.get(i)) {
                dependencyDone = false;
                System.out.println("Warning: Task \"" + n + "\" cannot be marked as complete because it depends on \"" + 
                        taskDependsName.get(i) + "\". Please complete \"" + taskDependsName.get(i) + "\" first.");
                break;
            }
        taskDependsName.clear();
        taskDependsId.clear();
        isCompleted.clear();
        return dependencyDone;
    }
    
    // Method to execute a select query
    public boolean executeDependencyCheck(String query, int x, int y) {
        try (Connection conn = getConnection(); 
            Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);){
            while (rs.next()) {
                taskId.add(rs.getInt("task_id"));
                taskDependsId.add(rs.getInt("depends_on_task_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
        if (taskId == null && taskDependsId == null)
            return true;
        // Convert task dependency data to adjacency list
        dependencyMap = new HashMap<>();
        for (int i = 0; i < taskId.size(); i++) {
            dependencyMap.put(taskId.get(i), taskDependsId.get(i)); // Maps task -> depends_on_task
        }
        dependencyMap.put(x, y);

    // Use slow and fast pointers for cycle detection
    for (int startTask : dependencyMap.keySet()) {
        int slow = startTask;
        int fast = startTask;

        while (dependencyMap.containsKey(fast)) {
            slow = dependencyMap.get(slow); // Move slow one step
            fast = dependencyMap.get(fast); // Move fast one step
            if (dependencyMap.containsKey(fast)) {
                fast = dependencyMap.get(fast); // Move fast a second step
            } else {
                break; // Fast pointer reached the end
            }

            // Check if slow and fast meet
            if (slow == fast) {
                System.out.println("Cycle detected in dependencies.");
                return true;
            }
        }
    }
    taskId.clear();
    taskDependsId.clear();
    dependencyMap.clear();
    return false; // No cycle detected
}

    public  String getName(int x) {  
        return t_taskName.get(x);        
    }
    
    public boolean taskExist(int x){
        if (x > t_taskId.size())
            return false;
        return true;
    }
}