package todolist;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;

public class List {
    private static ArrayList <Task> tasks;

    public List(){ //taskload here
        tasks = new ArrayList<>();
    }

    public void executeTaskLoad() {
    String query = "SE:ECT * FROM tasks";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(query);) {
            
            // load tasks from database
            while(rs.next()){
                int id = rs.getInt("task_id");
                String name = rs.getString("task_name");
                String description = rs.getString("task_description");
                String category = rs.getString("task_category");
                java.sql.Date sqlDate = rs.getDate("task_due_date");
                LocalDate dueDate = sqlDate.toLocalDate();
                int priorityID = rs.getInt("task_priorityID");
                boolean isCompleted = rs.getBoolean("is_completed");
                int recurring = rs.getInt("task_recurring");
                
                Task task = new Task(id,name,description,dueDate,category,isCompleted,priorityID,recurring);
                tasks.add(task);
            }
        }
        catch (SQLException e){
            System.out.println("Error occurs: " + e.getMessage());
        }
    }
   public static void main(String[] args) {
      List list = new List();
      list.listSearch();
      list.listSort();
   }
    public void listAnalytics(){

    }

    // sort
    public void listSort(){
         Scanner sc = new Scanner(System.in);
        System.out.print("Sort by > ");
        int option = sc.nextInt();
        switch(option){
            case 1:
                DueDateAscending();
                break;
            case 2:
                DueDateDescending();
                break;
            case 3:
                PriorityHighToLow();
                break;
            case 4:
                PriorityLowToHigh();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

// sort by duedate ascending
    private void DueDateAscending(){
        boolean swapped;
        // number of passess
        for (int i=1;i<tasks.size();i++){
            swapped = false;
            for (int j=0;j<tasks.size()-1;j++){
                if (tasks.get(j).getDueDate().compareTo(tasks.get(j+1).getDueDate())>0){
                    // swap tasks
                    Task temp = tasks.get(j);
                    tasks.set(j,tasks.get(j+1));
                    tasks.set(j+1,temp);
                    swapped = true;
                }
              }
                // if no two elements are swapped, the list is sorted
                if (!swapped)
                    break;
        }
    }
    // sort by due date descending
    private void DueDateDescending(){
        boolean swapped;
        for (int i=1;i<tasks.size();i++){
            swapped = false;
            for (int j=0;j<tasks.size()-1;j++){
                if (tasks.get(j).getDueDate().compareTo(tasks.get(j+1).getDueDate())<0){
                    Task temp = tasks.get(j);
                    tasks.set(j,tasks.get(j+1));
                    tasks.set(j+1, temp);
                    swapped = true;
                }
            }
            // If no two elements are swapped, the list is sorted
            if(!swapped)
                break;
        }
    }

     // sort priority high to low
    private void PriorityHighToLow(){
        boolean swapped;
        for (int i=1;i<tasks.size();i++){
            swapped = false;
            for(int j=0;j<tasks.size()-1;j++){
                if(tasks.get(j).getPriorityID()<tasks.get(j+1).getPriorityID()){
                    // swap tasks
                    Task temp = tasks.get(j);
                    tasks.set(j,tasks.get(j+1));
                    tasks.set(j+1,temp);
                    swapped = true;
                }
            }
            // If no two elements are swapped, the list is sorted
            if(!swapped)
                break;
        }
    }
    // sort priority low to high
    private void PriorityLowToHigh(){
        boolean swapped;
        for (int i=1;i<tasks.size();i++){
            swapped = false;
            for(int j=0;j<tasks.size()-1;j++){
                if(tasks.get(j).getPriorityID()>tasks.get(j+1).getPriorityID()){
                    // swap tasks
                    Task temp = tasks.get(j);
                    tasks.set(j,tasks.get(j+1));
                    tasks.set(j+1,temp);
                    swapped = true;
                }
            }
            // If no two elements are swapped, the list is sorted
            if (!swapped)
                break;
        }
    }
    
    // search
    public void listSearch(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a keyword to search by title or description: ");
        String keyword = sc.nextLine();
        int cnt =0;
        boolean found = false;
        for (int i=0;i<tasks.size();i++){
            if(tasks.get(i).getName().toLowerCase().contains(keyword)||tasks.get(i).getDescription().toLowerCase().contains(keyword)){
                cnt++;
                found = true;
                System.out.print(cnt + ". " + "[" + tasks.get(i).getIsCompleted() + "] ");
                System.out.println(tasks.get(i).getName() + " - ");
                System.out.println("Due: " + tasks.get(i).getDueDate());
                System.out.println("Category: " + tasks.get(i).getCategory());
                System.out.println("Priority: " + tasks.get(i).getPriorityName());
            }
    }
        if(!found)
            System.out.println("No task found for " + keyword);
    }

    public ArrayList<Task> dueCheck(){

    }
}

public class List {
    private ArrayList<List> tasks; // Storing tasks as List objects, which is unconventional

    // Task data fields
    private int taskId;
    private String taskName;
    private String taskDescription;
    private String taskDueDate;
    private String taskCategory;
    private String taskPriority;
    private boolean isCompleted;

    // Constructor for task data
    public List(int taskId, String taskName, String taskDescription, String taskDueDate, String taskCategory, String taskPriority, boolean isCompleted) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDueDate = taskDueDate;
        this.taskCategory = taskCategory;
        this.taskPriority = taskPriority;
        this.isCompleted = isCompleted;
    }

    // Default constructor for database manager
    public List() {
        this.tasks = new ArrayList<>();
    }

    // Load tasks into the list
    public void listLoad() {
        String query = "SELECT * FROM tasks";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            tasks.clear(); // Clear existing tasks before loading

            while (rs.next()) {
                taskId = rs.getInt("task_id");
                taskName = rs.getString("task_name");
                taskDescription = rs.getString("task_description");
                taskDueDate = rs.getString("task_due_date");
                taskCategory = rs.getString("task_category");
                taskPriority = rs.getString("task_priority");
                isCompleted = rs.getBoolean("is_completed");

                // Create a new List object for each task (unconventional)
                List task = new List(taskId, taskName, taskDescription, taskDueDate, taskCategory, taskPriority, isCompleted);
                tasks.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.getMessage());
        }
    }

    // Getters for task fields (if needed)
    public int getTaskId() {
        return taskId;
    }

    public String getTaskName(int x) {
        for (List task : tasks) {
        if (task.taskId == x)
            return task.taskName;
        }
    return null;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Getter for the task list
    public ArrayList<List> getTasks() {
        return tasks;
    }
}
