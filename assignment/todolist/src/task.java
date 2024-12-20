import java.time.LocalDate;

public class Task{
String title,description,category,priority,dependency;
    LocalDate dueDate;
    boolean isComplete;

public Task(String title, String description, LocalDate dueDate, String category, String priority){
    this.title=title;
    this.description = description;
    this.dueDate = dueDate;
    this.category = category;
    this.priority = priority;
    this.isComplete=false;
    this.dependency="";
}

public void setTitle(String title){
    this.title=title;
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

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

public String displayTask(){
    return title+" - Due: "+dueDate;
}

public static void TaskCreate(String title, String description, LocalDate dueDate, String category, String priority){
System.out.println("Task \"" + title + "\" added successfully!");
}
}