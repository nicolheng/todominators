package todolist;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.*;
import javafx.scene.control.Button;

public class Task {
    private String name, description, category,priorityName, recurringName;
    private LocalDate dueDate;
    private boolean isCompleted;
    private int id, priorityID, recurringID;
    private ArrayList<String> dc_taskDependsName;
    private ArrayList<Integer> dc_taskId, dc_taskDependsId;
    private ArrayList<Boolean> dc_isCompleted;
    private Map<Integer, Integer> dependencyMap;
    private Button button;
    
    public Task() {
        dc_taskDependsName = new ArrayList<>();
        dc_taskId = new ArrayList<>();
        dc_taskDependsId = new ArrayList<>();
        dc_isCompleted = new ArrayList<>();
    }

    public Task(String name, String description, LocalDate dueDate, String category, int priorityID, int recurringID){
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityID = priorityID;
        this.priorityName = setPriorityName(priorityID);
        this.recurringName = setRecurringName(recurringID);
        this.recurringID = recurringID;
    }

    public Task(int id, String name, String description, LocalDate dueDate, String category, boolean isCompleted, int priorityID, int recurringID){
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityID = priorityID;
        this.priorityName = setPriorityName(priorityID);
        this.isCompleted= isCompleted;
        this.recurringID = recurringID;
        this.button = new Button("Detail");
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

    private String setRecurringName(int recurringID){
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
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return id;
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

    public boolean getIsCompleted(){
        return this.isCompleted;
    }

    public int getPriorityID(){
        return this.priorityID;
    }

    public int getRecurringID(){
        return this.recurringID;
    }

    public void setButton (Button button){
        this.button = button;
    }

    public Button getButton(){
        return button;
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
        int recurringID = 0;
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
            case "None":
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

    public void taskComplete(int taskId) {
        // Check if task has dependencies that are not completed
        String query1 = "SELECT td.depends_on_task_id, t.task_name, t.is_completed " +
                        "FROM task_dependencies td " +
                        "JOIN tasks t ON td.depends_on_task_id = t.task_id " +
                        "WHERE td.task_id = " + taskId;
        if (taskRecurringCheck()){
            taskRecurring();
        }
        // If no dependencies or all dependencies are completed, mark task as completed
        String query2 = "UPDATE tasks SET is_completed = TRUE WHERE task_id = " + taskId;
        
        List l = new List();
        if (taskCompleteCheck(query1, l.getTaskName(taskId)))
            if (Database.executeUpdate(query2))
                System.out.println("Task \"" + l.getTaskName(taskId) + "\" marked as complete!");
        
        System.out.println();
    }
        
    private boolean taskCompleteCheck(String query, String n) {
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);){

            while (rs.next()) {
                dc_taskDependsName.add(rs.getString("task_name"));
                dc_taskDependsId.add(rs.getInt("depends_on_task_id"));
                dc_isCompleted.add(rs.getBoolean("is_completed"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
            
        boolean dependencyDone = true;
        for (int i = 0; i < dc_isCompleted.size(); i++)
            if (!dc_isCompleted.get(i)) {
                dependencyDone = false;
                System.out.println("Warning: Task \"" + n + "\" cannot be marked as complete because it depends on \"" +
                        dc_taskDependsName.get(i) + "\". Please complete \"" + dc_taskDependsName.get(i) + "\" first.");
                break;
            }
        dc_taskDependsName.clear();
        dc_taskDependsId.clear();
        dc_isCompleted.clear();
        return dependencyDone;
    }
    
    public void taskEdit(){

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

    
    public void taskDependency(int taskId, int dependsOnTaskId){
        String query1 = "SELECT task_id, depends_on_task_id FROM task_dependencies";
        List l = new List();
        if (taskDependencyCheck(query1, taskId, dependsOnTaskId)){
            System.out.println("Warning: Task \"" + l.getTaskName(taskId) + "\" cannot depends on \"" + l.getTaskName(dependsOnTaskId) + "\" because it will cause a loop. ");
        }
        else {
            String query2 = "INSERT INTO task_dependencies (task_id, depends_on_task_id) VALUES (" + taskId + ", " + dependsOnTaskId + ")";
            if (Database.executeUpdate(query2))
                System.out.println("Task \"" + l.getTaskName(taskId) + "\" now depends on \"" + l.getTaskName(dependsOnTaskId) + "\".");
            else
                System.out.println("Task \"" + l.getTaskName(taskId) + "\" already depends on \"" + l.getTaskName(dependsOnTaskId) + "\".");
        }
        System.out.println();
    }

    //recurring concept: create a task with same detail but different due date
    public void taskRecurring(){
        try (Connection conn = Database.getConnection();
            var stmt = conn.prepareStatement("INSERT INTO tasks(task_name, task_description, task_due_date, task_category, task_priorityID, task_recurringID) VALUES(?,?,?,?,?,?)");){
            stmt.setString(1, this.name);
            stmt.setString(2, this.description);
            String newDate = this.dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            switch (this.recurringID) {
                case 2:
                    newDate = this.dueDate.plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    break;
                case 3:
                    newDate = this.dueDate.plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    break;
                case 4:
                    newDate = this.dueDate.plusMonths(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    break;
                default:
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
    public boolean taskDependencyCheck(String query, int x, int y) {

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
    
        // Check if taskId is empty before proceeding
        if (dc_taskId.isEmpty() || dc_taskDependsId.isEmpty())
            return false; // No dependencies to check
    
        // Continue with the existing logic...
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
    
        dc_taskId.clear();
        dc_taskDependsId.clear();
        dependencyMap.clear();
        return false; // No cycle detected
    }

    // Helper method to validate task IDs
    public int getValidTaskId(Scanner sc, String prompt) {
        int taskID;
        System.out.print(prompt);
        taskID = sc.nextInt();
        while (!taskExist(taskID)) {
            System.out.println("Task number entered does not exist. Please enter a valid value.");
            System.out.print(prompt);
            taskID = sc.nextInt();
        }
        return taskID;
    }

    public boolean taskExist(int x){
        String query = "SELECT task_id from tasks";
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);){
            while (rs.next()) {
                dc_taskId.add(rs.getInt("task_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
        int max = dc_taskId.size();
        dc_taskId.clear();
        if (x > max)
            return false;
        return true;
    }

    //recurring id = 1 when there is no recurring
    public boolean taskRecurringCheck(){
        return (getRecurringID() != 1);
    }

    @Override
    public String toString() {
        return this.getName(); // Assuming 'getName()' returns the task title
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = "26-12-2024";
      
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        Task test = new Task(4, "FOP tutorial","tutorial",localDate ,"Homework", false, 3, 3);
        test.taskDelete();
    }
}