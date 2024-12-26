package todolist;
import java.time.LocalDate;

public class Task{
    private String name, description, category;
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