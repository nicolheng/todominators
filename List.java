/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author User
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;

public class List {
    private static final String DB_URL = "jdbc:sqlite:/C:/Users/myonl/OneDrive/Desktop/Valencien/UM study materials y1s1/FOP/FOP Assignment/tasks.db"; // SQLite DB file
    private ArrayList<Task> tasks;
    
    // constructor 
    public List(){
        tasks = new ArrayList<>();
    }
    // Connect to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL); // Establish connection to SQLite DB
    }
    public void executeTaskLoad() {
        String query = "SELECT * FROM tasks";
    
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(query);) {
            
            // load tasks from database
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                java.sql.Date sqlDate = rs.getDate("dueDate");
                LocalDate dueDate = sqlDate.toLocalDate();
                String priority = rs.getString("priority");
                boolean status = rs.getBoolean("isCompleted");
                
                Task task = new Task(id,name,description,dueDate,category,priority,status);
                tasks.add(task);
            }
        }
        catch (SQLException e){
            System.out.println("Error occurs: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
      List list = new List();
      list.ListSearch();
      list.ListSort();
      list.executeTaskLoad();
    }
    // sort 
    public void ListSort(){
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
    
    // search and print
    public void ListSearch(){
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
}
