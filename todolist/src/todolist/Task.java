package todolist;
import java.time.LocalDate;
import java.sql.*;
import java.util.*;

public class Task{
    private String name, description, category,priorityName;
    private LocalDate dueDate;
    private boolean isCompleted;
    private int id, priorityID, recurring;

    public Task(int id, String name, String description, LocalDate dueDate, String category, boolean isCompleted, int priorityID, int recurring){
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityID = priorityID;
        this.priorityName = setPriorityName(priorityID);
        this.isCompleted= isCompleted;
        this.recurring = recurring;
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

    public void setIsCompleted (boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setRecurring (int recurring) {
        this.recurring = recurring;
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

    public boolean getIsCompleted(){
        return this.isCompleted;
    }

    public int getPriorityID(){
        return this.priorityID;
    }

    public int getRecurring(){
        return this.recurring;
    }

    public static void taskCreate() {

    }

    public void taskComplete(){

    }
    
    public void taskEdit(){

    }

    public void taskDelete(){

    }

    public void taskDependency(){

    }

    public void taskRecurring(){

    }
    public void taskDependencyCheck(){

    }
}
public class Task extends Database {
    private ArrayList<String> taskDependsName;
    private ArrayList<Integer> taskId, taskDependsId;
    private ArrayList<Boolean> isCompleted;
    private Map<Integer, Integer> dependencyMap;
    
    public Task() {        
        taskDependsName = new ArrayList<>();
        taskId = new ArrayList<>();
        taskDependsId = new ArrayList<>();
        isCompleted = new ArrayList<>();
    }
    
    public void taskComplete(int taskId) {
    // Check if task has dependencies that are not completed   
    String query1 = "SELECT td.depends_on_task_id, t.task_name, t.is_completed " +
                   "FROM task_dependencies td " +
                   "JOIN tasks t ON td.depends_on_task_id = t.task_id " +
                   "WHERE td.task_id = " + taskId;
    
    // If no dependencies or all dependencies are completed, mark task as completed
    String query2 = "UPDATE tasks SET is_completed = TRUE WHERE task_id = " + taskId;
    
    List l = new List();
    l.listLoad();
    if (executeCompleteCheck(query1, l.getTaskName(taskId)))
        if (Database.executeUpdate(query2)) 
            System.out.println("Task \"" + l.getTaskName(taskId) + "\" marked as complete!");
    
    System.out.println();
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
    
    //check for loop dependencies/existing dependencies
    public void taskDependency(int taskId, int dependsOnTaskId){
        String query1 = "SELECT task_id, depends_on_task_id FROM task_dependencies";
        List l = new List();
        l.listLoad();
        if (executeDependencyCheck(query1, taskId, dependsOnTaskId)){
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

// Ensure taskId list is populated before executing dependency checks
public boolean executeDependencyCheck(String query, int x, int y) {

    try (Connection conn = getConnection(); 
         Statement stmt = conn.createStatement(); 
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            taskId.add(rs.getInt("task_id"));
            taskDependsId.add(rs.getInt("depends_on_task_id"));
        }
    } catch (SQLException e) {
        System.out.println("Error occurs. " + e.getMessage());
    }

    // Check if taskId is empty before proceeding
    if (taskId.isEmpty() || taskDependsId.isEmpty())
        return false; // No dependencies to check

    // Continue with the existing logic...
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
        try (Connection conn = getConnection(); 
            Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);){
            while (rs.next()) {
                taskId.add(rs.getInt("task_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
        int max = taskId.size();
        taskId.clear();
        if (x > max)
            return false;
        return true;
    }
}
