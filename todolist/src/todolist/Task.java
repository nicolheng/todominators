package todolist;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TimerTask;

public class Task{
    private String name, description, category;
    private LocalDate dueDate;
    private boolean isCompleted;
    private int id, priorityID, recurring;

    public Task(int id, String name, String description, LocalDate dueDate, String category, boolean isCompleted, int priorityID, int recurring){
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityID = priorityID;
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
    }

    public void setIsCompleted (boolean isCompleted) {
        this.isCompleted = isCompleted;
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