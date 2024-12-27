package todolist;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.Scanner;

public class Task{
    private String name, description, category,priorityName;
    private LocalDate dueDate;
    private boolean isCompleted;
    private int id, priorityID, recurringID;

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

    public void setRecurringID (int recurringID) {
        this.recurringID = recurringID;
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

    public int getRecurringID(){
        return this.recurringID;
    }

    public static void taskCreate() {

    }

    public void taskComplete(){

    }
    
    public void taskEdit(){

    }

    public void taskDelete(){
        if (taskRecurringCheck()){
            Scanner input = new Scanner("y");
            String check = input.next();
            input.close();
            if (check.equals("y")){
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
                        case 4:
                            newDate = this.dueDate.plusMonths(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
        }
        try (Connection conn = Database.getConnection();
            var stmt = conn.prepareStatement("DELETE FROM tasks where task_id = ? ");){
            stmt.setInt(1, this.id);
            stmt.executeUpdate();

        } catch (SQLException e){
            System.out.println("Error occurs: " + e.getMessage());
        }
    }

    public void taskDependency(){

    }

    public void taskRecurring(){
        
    }

    public void taskDependencyCheck(){

    }

    public boolean taskRecurringCheck(){
        return (getRecurringID() != 1);
    }
}