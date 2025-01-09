package todolist;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.*;

public class Task {
    private String name, description, category,priorityName, recurringName;
    private LocalDate dueDate;
    private Boolean isCompleted;
    private int id, priorityID, recurringID;
    static ArrayList<String> dc_taskDependsName;
    static ArrayList<Integer> dc_taskId, dc_taskDependsId;
    static ArrayList<Boolean> dc_isCompleted;
    static Map<Integer, Integer> dependencyMap;

    public Task(int id, String name, String description, LocalDate dueDate, String category, Boolean isCompleted, int priorityID, int recurringID){
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityID = priorityID;
        this.priorityName = setPriorityName(priorityID);
        this.isCompleted= isCompleted;
        this.recurringID = recurringID;
        this.recurringName = setRecurringName(recurringID);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriorityID(int priorityID) {
        this.priorityID = priorityID;
        this.priorityName = setPriorityName(priorityID);
    }
    
    public String getPriorityName(){
        return priorityName;
    }

    private String setPriorityName(int priorityID){
        switch(priorityID){
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";
            default:
                return "Unknown";
        }
    }

    public String getRecurringName(){
        return recurringName;
    }

    public String setRecurringName(int recurringID){
        switch(recurringID){
            case 1:
                return "None";
            case 2:
                return "Daily";
            case 3:
                return "Weekly";
            default:
                return "Monthly";
        }
    }

    public void setIsCompleted (boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setRecurringID (int recurringID) {
        this.recurringID = recurringID;
        this.recurringName = setRecurringName(recurringID);
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public LocalDate getDueDate(){
        return this.dueDate;
    }

    public String getCategory(){
        return this.category;
    }

    public Boolean getIsCompleted(){
        return this.isCompleted;
    }

    public int getPriorityID(){
        return this.priorityID;
    }

    public int getRecurringID(){
        return this.recurringID;
    }

    public static void taskCreate(String title, String description, LocalDate dueDate, String category, String priority, String recurrString) {
        int priorityID = 0;
        switch (priority) {
            case "High":
                priorityID = 3;
                break;
            case "Medium":
                priorityID = 2;
                break;
            case "Low":
                priorityID = 1;
                break;
        }
        int recurringID = 1;
        switch (recurrString) {
            case "Monthly":
                recurringID = 4;
                break;
            case "Weekly":
                recurringID = 3;
                break;
            case "Daily":
                recurringID = 2;
                break;
            default:
                recurringID = 1;
                break;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = dueDate.format(formatter);

        String query = "INSERT INTO tasks (task_name, task_description, task_due_date, task_category, task_priorityID, task_recurringID) " + "VALUES (?, ?, ?, ?, ?, ?)";
        Database.executeUpdate(query, title, description, formattedDate, category, priorityID, recurringID);
        System.out.println();
        System.out.println("Task \"" + title + "\" added successfully!");
    }

    public boolean taskComplete(int taskId) {
        dc_taskDependsName = new ArrayList<>();
        dc_taskId = new ArrayList<>();
        dc_taskDependsId = new ArrayList<>();
        dc_isCompleted = new ArrayList<>();
        // Check if task has dependencies that are not completed
        String query1 = "SELECT td.depends_on_task_id, t.task_name, t.is_completed " +
                        "FROM task_dependencies td " +
                        "JOIN tasks t ON td.depends_on_task_id = t.task_id " +
                        "WHERE td.task_id = ?";
        // If no dependencies or all dependencies are completed, mark task as completed
        String query2 = "UPDATE tasks SET is_completed = TRUE WHERE task_id = " + taskId;
        
        List l = new List();
        if (taskCompleteCheck(query1, l.getTaskName(taskId), taskId)) {
            if (Database.executeUpdate(query2)) {
                System.out.println("Task \"" + l.getTaskName(taskId) + "\" marked as complete!");
            }
        }
        else {
            return false;
        }
        if (taskRecurringCheck()){
            taskRecurring();
        }
        return true;
    }
        
    private boolean taskCompleteCheck(String query, String n, int taskId) {
        try (Connection conn = Database.getConnection();
            var stmt = conn.prepareStatement(query);){
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dc_taskDependsName.add(rs.getString("task_name"));
                dc_taskDependsId.add(rs.getInt("depends_on_task_id"));
                dc_isCompleted.add(rs.getBoolean("is_completed"));
            }
            if (dc_taskDependsName.isEmpty()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
            
        boolean dependencyDone = true;
        for (int i = 0; i < dc_isCompleted.size(); i++) {
            if (!dc_isCompleted.get(i)) {
                dependencyDone = false;
                System.out.println("Warning: Task \"" + n + "\" cannot be marked as complete because it depends on \"" +
                        dc_taskDependsName.get(i) + "\". Please complete \"" + dc_taskDependsName.get(i) + "\" first.");
                break;
            }
        }
        dc_taskDependsName.clear();
        dc_taskDependsId.clear();
        dc_isCompleted.clear();
        return dependencyDone;
    }
    
    public static void taskEdit(String title, String description, LocalDate dueDate, String category, String priority, String recurrString, int id){
        int priorityID = 0;
        switch (priority) {
            case "High":
                priorityID = 3;
                break;
            case "Medium":
                priorityID = 2;
                break;
            case "Low":
                priorityID = 1;
                break;
        }
        int recurringID = 1;
        switch (recurrString) {
            case "Monthly":
                recurringID = 4;
                break;
            case "Weekly":
                recurringID = 3;
                break;
            case "Daily":
                recurringID = 2;
                break;
            default:
                recurringID = 1;
                break;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = dueDate.format(formatter);
        String query = "UPDATE tasks SET task_name = ?, task_description = ?, task_due_date = ?, task_category = ?, task_priorityID = ?, task_recurringID = ? WHERE task_id = ?";
        try (Connection conn = Database.getConnection();
            var stmt = conn.prepareStatement(query);){
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, formattedDate);
                stmt.setString(4, category);
                stmt.setInt(5, priorityID);
                stmt.setInt(6, recurringID);
                stmt.setInt(7, id);
                stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
        }
    
    }

    public void taskDelete(){
        try (Connection conn = Database.getConnection();){
            var stmt = conn.prepareStatement("DELETE FROM tasks where task_id = ? ");
            stmt.setInt(1, this.id);
            stmt.executeUpdate();

            var stmtDependency = conn.prepareStatement("DELETE FROM task_dependencies where task_id = ? or depends_on_task_id = ?");
            stmtDependency.setInt(1, this.id);
            stmtDependency.setInt(2, this.id);
            stmtDependency.executeUpdate();
            
        } catch (SQLException e){
            System.out.println("Error occurs: " + e.getMessage());
        }
    }

    
    public static String taskDependency(int taskId, int dependsOnTaskId){
        dc_taskDependsName = new ArrayList<>();
        dc_taskId = new ArrayList<>();
        dc_taskDependsId = new ArrayList<>();
        dc_isCompleted = new ArrayList<>();
        String query1 = "SELECT task_id, depends_on_task_id FROM task_dependencies";
        List l = new List();
        String msg = null;
        if (taskDependencyCheck(query1, taskId, dependsOnTaskId)){
            msg = "Warning: Task \"" + l.getTaskName(taskId) + "\" cannot depends on \"" + l.getTaskName(dependsOnTaskId) + "\" because it will cause a loop. ";
        }
        else {
            String query2 = "INSERT INTO task_dependencies (task_id, depends_on_task_id) VALUES (" + taskId + ", " + dependsOnTaskId + ")";
            if (!Database.executeUpdate(query2))
                msg = "Task \"" + l.getTaskName(taskId) + "\" already depends on \"" + l.getTaskName(dependsOnTaskId) + "\".";
        }
        return msg;
    }

    //recurring concept: create a task with same detail but different due date
    public void taskRecurring(){
        try (Connection conn = Database.getConnection();
            var stmt = conn.prepareStatement("INSERT INTO tasks(task_name, task_description, task_due_date, " +
                                "task_category, task_priorityID, task_recurringID) VALUES(?,?,?,?,?,?)");){
            stmt.setString(1, this.name);
            stmt.setString(2, this.description);
            String newDate = this.dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            switch (this.recurringID) {
                case 2:
                    System.out.println("Recurring daily (2)");
                    newDate = this.dueDate.plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    break;
                case 3:
                    System.out.println("Recurring weekly (3)");
                    newDate = this.dueDate.plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    break;
                case 4:
                    System.out.println("Recurring monthly (4)");
                    newDate = this.dueDate.plusMonths(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    break;
                default:
                    System.out.println("No recurring (default case)");
                    break;
            }
            stmt.setString(3, newDate);
            stmt.setString(4, this.category);
            stmt.setInt(5, this.priorityID);
            stmt.setInt(6, this.recurringID);
            stmt.executeUpdate();

        } catch (SQLException e){
            System.out.println("Error occurs: " + e.getMessage());
        }
    }

    //check for loop dependencies/existing dependencies
    public static boolean taskDependencyCheck(String query, int x, int y) {
        dc_taskId = new ArrayList<>();
        dc_taskDependsId = new ArrayList<>();
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                dc_taskId.add(rs.getInt("task_id"));
                dc_taskDependsId.add(rs.getInt("depends_on_task_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
    
        if (dc_taskId.isEmpty() || dc_taskDependsId.isEmpty())
            return false;

        dependencyMap = new HashMap<>();
        for (int i = 0; i < dc_taskId.size(); i++) {
            dependencyMap.put(dc_taskId.get(i), dc_taskDependsId.get(i)); // Maps task -> depends_on_task
        }
        dependencyMap.put(x, y);
    
        // Use slow and fast pointers for cycle detection
        for (int startTask : dependencyMap.keySet()) {
            int slow = startTask;
            int fast = startTask;
    
            while (dependencyMap.containsKey(fast)) {
                slow = dependencyMap.get(slow);
                fast = dependencyMap.get(fast);
                if (dependencyMap.containsKey(fast)) {
                    fast = dependencyMap.get(fast);
                } else {
                    break; // Fast pointer reached the end
                }
    
                if (slow == fast) {
                    System.out.println("Cycle detected in dependencies.");
                    return true;
                }
            }
        }
    
        dc_taskId.clear();
        dc_taskDependsId.clear();
        dependencyMap.clear();
        return false;
    }

    //recurring id = 1 when there is no recurring
    public boolean taskRecurringCheck(){
        return (getRecurringID() != 1);
    }

    @Override
    public String toString() {
        return this.getName(); // Assuming 'getName()' returns the task title
    }
}