package todolist;

import java.util.ArrayList;
import java.util.Scanner;
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

        public static void main(String[] args) {
        List list = new List();
        list.display();;
        list.listSearch();
        list.listSort();
        list.display();;
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
        sc.close();
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