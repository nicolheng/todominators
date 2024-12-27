/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TimerTask;

public class Task{
    private String name, description, category,priorityName;
    private LocalDate dueDate;
    private boolean isCompleted;
    private int id, priorityID, recurring;

    public Task(int id, String name, String description, LocalDate dueDate, String category, String priority, boolean isCompleted){
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

    public String getName(){
        return name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getDueDate(){
        return dueDate;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCategory(){
        return category;
    }
    
    public int getPriorityID(){
        return priorityID;
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

    public boolean getIsCompleted(){
        return isCompleted;
    }
    
    public void setRecurring (int recurring) {
        this.recurring = recurring;
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