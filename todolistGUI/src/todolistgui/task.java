package todolistgui;


import java.time.LocalDate;
import java.util.Scanner;

public class task{
public static String title,description,category,priority,dependency;
public static LocalDate dueDate;
public static boolean isComplete;
public int taskId;

public task(int taskId, String taskName, String taskDescription, String taskDueDate, 
                String category, String priority, boolean isCompleted) {
    this.taskId = taskId;
    this.title = taskName;   // Fix this by assigning the correct arguments
    this.description = taskDescription;
    this.dueDate = LocalDate.parse(taskDueDate); // Assuming taskDueDate is a string in the format YYYY-MM-DD
    this.category = category;
    this.priority = priority;
    this.isComplete = isCompleted;
    this.dependency = ""; // Empty dependency initially
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
    
    public int getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return isComplete; }
    
    public String displayTask(){
    return title+" - Due: "+dueDate;
    }

    public static void TaskCreate(){
    Scanner sc=new Scanner(System.in);

        System.out.print("Enter task title: ");
        String title = sc.nextLine();
        
        System.out.print("Enter task description: ");
        String description = sc.nextLine();
        
        System.out.print("Enter due date (YYYY-MM-DD): ");
        LocalDate dueDate = LocalDate.parse(sc.nextLine());
        
        System.out.print("Enter task category (Homework, Personal, Work): ");
        String category = sc.nextLine();
        
        System.out.print("Priority level (Low, Medium, High): ");
        String priority = sc.nextLine();
        
        String query = "INSERT INTO tasks (task_name,task_description,task_due_date,task_category,task_priority) VALUES ('" + title + "', '" + description + "', '" + dueDate + "', '" + category + "', '" + priority+ ")";
        Database1.executeUpdate(query, title, description, dueDate.toString(), category, priority);
        System.out.println();
        System.out.println("Task \"" + title + "\" added successfully!");
    }

    public static void TaskEdit(){  
       Scanner sc=new Scanner(System.in);
        System.out.println("===Edit Task===");
        System.out.print("Enter task number to edit: ");
                int taskNumberToEdit = sc.nextInt();
                System.out.println();

                System.out.println("What would you like to edit?");
                System.out.println("1. Title");
                System.out.println("2. Description");
                System.out.println("3. Due Date");
                System.out.println("4. Category");
                System.out.println("5. Priority");
                System.out.println("6. Set Task Dependency");
                System.out.println("7. Cancel");
                
                System.out.println();
                System.out.print(">");
                int editChoice = sc.nextInt();
                sc.nextLine();
                
                String query = null;

                switch (editChoice) {
                    case 1: // Edit title
                        System.out.print("Enter new title: ");
                        String newTitle = sc.nextLine();
                        query = "UPDATE tasks SET task_name = '"+newTitle+"' WHERE task_id="+taskNumberToEdit;
                        break;

                    case 2: // Edit description
                        System.out.print("Enter new description: ");
                        String newDescription = sc.nextLine();
                        query = "UPDATE tasks SET task_description = '"+newDescription+"' WHERE task_id="+taskNumberToEdit;
                        break;

                    case 3: // Edit due date
                        System.out.print("Enter new due date (YYYY-MM-DD): ");
                        LocalDate newDueDate = LocalDate.parse(sc.nextLine());
                        query = "UPDATE tasks SET task_due_date = '"+newDueDate+"' WHERE task_id="+taskNumberToEdit;
                        break;

                    case 4: // Edit category
                        System.out.print("Enter new category (Homework, Personal, Work): ");
                        String newCategory = sc.nextLine();
                        query = "UPDATE tasks SET task_category = '"+newCategory+"' WHERE task_id="+taskNumberToEdit;
                        break;

                    case 5: // Edit priority
                        System.out.print("Enter new priority (Low, Medium, High): ");
                        String newPriority = sc.nextLine();
                        query = "UPDATE tasks SET task_priority = '"+newPriority+"' WHERE task_id="+taskNumberToEdit;
                        break;
                    
                    case 6: //Edit dependency
                        System.out.println("Enter new task dependency: ");
                        String newDependency = sc.nextLine();
                        query = "UPDATE task_dependencies SET depends_on_task_id = '"+newDependency+"' WHERE task_id="+taskNumberToEdit;
                        break;

                    case 7:
                        break;   

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
                Database1.executeUpdate(query, title, description, dueDate.toString(), category, priority);
    
    }
public static void ViewTask(){
 System.out.println("===View All Tasks===");
 
 String query = "SELECT task_name, task_due_date,is_completed FROM tasks";
 Database1.executeQuery(query);
    
    }
}
