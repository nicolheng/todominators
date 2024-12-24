package todolist;
public class Test {
    public static void main(String[] args) {
    
        Scanner sc=new Scanner(System.in);
        ArrayList<Task>taskList=new ArrayList<>();

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
        
        Task.TaskCreate(title, description, dueDate, category, priority);
        taskList.add(TaskCreate);

        System.out.println();
        System.out.println("Task \"" + title + "\" added successfully!");
        
        System.out.println("===View All Tasks===");
        if (taskList.isEmpty()) {
            System.out.println("No tasks added.");
            }
        else {
        char taskLetter='A';
        int taskNumber=1;
        for (TaskCreate task:taskList) 
        {
            System.out.println(taskNumber+". "+(task.isComplete? "[Complete] ":"[Incomplete] "+ "Task "+taskLetter+": "+task.displayTask()));
            taskLetter++;
            taskNumber++;
        }
        }
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
                
                // Get the current task
                TaskCreate taskToEdit = taskList.get(taskNumberToEdit - 1);

                switch (editChoice) {
                    case 1: // Edit title
                        System.out.print("Enter new title: ");
                        String newTitle = sc.nextLine();
                        taskToEdit.setTitle(newTitle);
                        break;

                    case 2: // Edit description
                        System.out.print("Enter new description: ");
                        String newDescription = sc.nextLine();
                        taskToEdit.setDescription(newDescription);
                        break;

                    case 3: // Edit due date
                        System.out.print("Enter new due date (YYYY-MM-DD): ");
                        LocalDate newDueDate = LocalDate.parse(sc.nextLine());
                        taskToEdit.setDueDate(newDueDate);
                        break;

                    case 4: // Edit category
                        System.out.print("Enter new category (Homework, Personal, Work): ");
                        String newCategory = sc.nextLine();
                        taskToEdit.setCategory(newCategory);
                        break;

                    case 5: // Edit priority
                        System.out.print("Enter new priority (Low, Medium, High): ");
                        String newPriority = sc.nextLine();
                        taskToEdit.setPriority(newPriority);
                        break;
                    
                    case 6: //Edit dependency
                        System.out.println("Enter new task dependency: ");
                        String newTaskDependency = sc.nextLine();
                        taskToEdit.setDependency(newTaskDependency);
                        break;
                        
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
}

    