package todolist;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class List {
    private static ArrayList <Task> tasks;

    public List(){ //taskload here
        tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {

            tasks.clear(); // Clear existing tasks before loading

            // load tasks from database
            while(rs.next()){
                int id = rs.getInt("task_id");
                String name = rs.getString("task_name");
                String description = rs.getString("task_description");
                String category = rs.getString("task_category");
                String sqlDate = rs.getString("task_due_date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate dueDate = LocalDate.parse(sqlDate, formatter);
                int priorityID = rs.getInt("task_priorityID");
                boolean isCompleted = rs.getBoolean("is_completed");
                int recurringID = rs.getInt("task_recurringID");
                
                Task task = new Task(id,name,description,dueDate,category,isCompleted,priorityID,recurringID);
                tasks.add(task);
            }
        }
        catch (SQLException e){
            System.out.println("Error occurs: " + e.getMessage());
        }
    }

    public static String listAnalytics(){
        String metricsQuery = """
    WITH 
    total_tasks_cte AS (
        SELECT COUNT(*) AS total_tasks
        FROM tasks
    ),
    completed_tasks_cte AS (
        SELECT COUNT(*) AS completed_tasks
        FROM tasks
        WHERE LOWER(is_completed) = 'true'
    ),
    pending_tasks_cte AS (
        SELECT COUNT(*) AS pending_tasks
        FROM tasks
        WHERE LOWER(is_completed) = 'false'
    ),
    completion_rate_cte AS (
        SELECT 
            printf('%.2f%%', (COUNT(*) FILTER (WHERE LOWER(is_completed) = 'true') * 100.0 / COUNT(*))) AS completion_rate
        FROM tasks
    )
    SELECT 'Total Tasks' AS metric, total_tasks AS value FROM total_tasks_cte
    UNION ALL
    SELECT 'Completed Tasks', completed_tasks FROM completed_tasks_cte
    UNION ALL
    SELECT 'Pending Tasks', pending_tasks FROM pending_tasks_cte
    UNION ALL
    SELECT 'Completion Rate', completion_rate FROM completion_rate_cte
    """;


        // SQL query to calculate task category counts
        String categoriesQuery = """
            SELECT task_category, COUNT(*) AS task_count
            FROM tasks
            GROUP BY task_category
            ORDER BY task_count DESC
            """;

        String  output = "" ;
        try (Connection conn = Database.getConnection()) {
            // Execute and display task metrics
            try (PreparedStatement stmt = conn.prepareStatement(metricsQuery);
                 ResultSet rs = stmt.executeQuery()) {

                output += "Task Metrics:\n";
                while (rs.next()) {
                    output += rs.getString("metric") + ": " + rs.getString("value") + "\n";
                }
            }

            // Execute and display task categories
            try (PreparedStatement stmt = conn.prepareStatement(categoriesQuery);
                 ResultSet rs = stmt.executeQuery()) {

                output += "\nTask Categories:\n";
                while (rs.next()) {
                    output += rs.getString("task_category") + ": " + rs.getInt("task_count") +"\n";
                }
            }
            

        } catch (SQLException e) {
            // Handle SQLException
            System.err.println("An error occurred while accessing the database: " + e.getMessage());
            e.printStackTrace();
        }
        return output;

    }

    // sort
    public static ArrayList<Task> listSort(int option){
        ArrayList<Task> sorted = tasks;
        switch(option){
            case 1:
                sorted = DueDateAscending(tasks);
                break;
            case 2:
                sorted = DueDateDescending(tasks);
                break;
            case 3:
                sorted = PriorityHighToLow(tasks);
                break;
            case 4:
                sorted = PriorityLowToHigh(tasks);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return sorted;
    }

// sort by duedate ascending
    private static ArrayList<Task> DueDateAscending(ArrayList<Task>list){
        boolean swapped;
        // number of passess
        for (int i=1;i<list.size();i++){
            swapped = false;
            for (int j=0;j<list.size()-1;j++){
                if (list.get(j).getDueDate().compareTo(list.get(j+1).getDueDate())>0){
                    // swap tasks
                    Task temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                    swapped = true;
                }
            }
                // if no two elements are swapped, the list is sorted
                if (!swapped)
                    break;
        }
        return list;
    }
    // sort by due date descending
    private static ArrayList<Task> DueDateDescending(ArrayList <Task> list){
        boolean swapped;
        for (int i=1;i<list.size();i++){
            swapped = false;
            for (int j=0;j<list.size()-1;j++){
                if (list.get(j).getDueDate().compareTo(list.get(j+1).getDueDate())<0){
                    Task temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1, temp);
                    swapped = true;
                }
            }
            // If no two elements are swapped, the list is sorted
            if(!swapped)
                break;
        }
        return list;
    }

     // sort priority high to low
    private static ArrayList<Task> PriorityHighToLow(ArrayList <Task> list){
        boolean swapped;
        for (int i=1;i<list.size();i++){
            swapped = false;
            for(int j=0;j<list.size()-1;j++){
                if(list.get(j).getPriorityID()<list.get(j+1).getPriorityID()){
                    // swap tasks
                    Task temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                    swapped = true;
                }
            }
            // If no two elements are swapped, the list is sorted
            if(!swapped)
                break;
        }
        return list;
    }
    // sort priority low to high
    private static ArrayList <Task> PriorityLowToHigh(ArrayList <Task> list){
        boolean swapped;
        for (int i=1;i<list.size();i++){
            swapped = false;
            for(int j=0;j<list.size()-1;j++){
                if(list.get(j).getPriorityID()>list.get(j+1).getPriorityID()){
                    // swap tasks
                    Task temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                    swapped = true;
                }
            }
            // If no two elements are swapped, the list is sorted
            if (!swapped)
                break;
        }
        return list;
    }
    
    // search
    public static ArrayList<Task> listSearch(String query){
        ArrayList<Task> matchSearch = new ArrayList<>();
        String keyword = query.toLowerCase();
        for (Task task : tasks){
            if(task.getName().toLowerCase().contains(keyword)||task.getDescription().toLowerCase().contains(keyword))
                matchSearch.add(task);
        }
        for (Task result : matchSearch)
            System.out.println(result);
        return matchSearch;
    }

    public String getTaskName(int x) {
        for (Task task : tasks) {
        if (task.getID() == x)
            return task.getName();
        }
    return null;
    }

    public ArrayList<Task> getList(){
        return tasks;
    }
    //debugging purpose
    public void display() {
        for (Task task : tasks)
            System.out.println("Task{" +
                "taskId=" + task.getID() +
                ", taskName='" + task.getName() + '\'' +
                ", taskDescription='" + task.getDescription() + '\'' +
                ", taskDueDate='" + task.getDueDate() + '\'' +
                ", taskCategory='" + task.getCategory() + '\'' +
                ", taskPriority='" + task.getPriorityName() + '\'' +
                ", isCompleted=" + task.getIsCompleted() +
                '}');
    }
}