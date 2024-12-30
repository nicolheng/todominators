package taskload;

import java.sql.*;
import java.util.ArrayList;

class TaskLoad {
    private ArrayList<Task> tasks;

    // Constructor
    public TaskLoad() {
        this.tasks = new ArrayList<>();
    }

    // Load tasks from the database
    public void loadTasksFromDatabase() {
        String url = "jdbc:sqlite:C:\\Users\\User\\Downloads\\tasks.db"; // Replace with your actual database file path

        String query = "SELECT task_id, task_name, task_description, task_priority, task_due_date, is_completed FROM tasks";


        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Retrieve data from the ResultSet
                int task_id = resultSet.getInt("task_id");
                String task_name = resultSet.getString("task_name");
                String task_description = resultSet.getString("task_description");
                String task_priority = resultSet.getString("task_priority");
                String task_due_date = resultSet.getString("task_due_date"); // Use LocalDate if needed
                boolean is_completed = resultSet.getBoolean("is_completed");

                // Create a Task object
                Task task = new Task(task_name, task_description, task_priority, task_due_date);
                task.setTaskId(task_id);
                task.setComplete(is_completed);

                // Add the task to the ArrayList
                tasks.add(task);
            }

            System.out.println("Tasks loaded successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Display tasks
    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        TaskLoad taskload = new TaskLoad();

        // Load tasks from the database
        taskload.loadTasksFromDatabase();

        // Display tasks
        taskload.displayTasks();
    }
}

// Task Class
class Task {
    private int task_id;
    private String task_name;
    private String task_description;
    private String task_priority;
    private String task_due_date; // Use LocalDate if working with dates
    private boolean is_completed;

    // Constructor
    public Task(String task_name, String task_description, String task_priority, String task_due_date) {
        this.task_name = task_name;
        this.task_description = task_description;
        this.task_priority = task_priority;
        this.task_due_date = task_due_date;
        this.is_completed = false; // Default value
    }

    // Getters and Setters
    public int getTaskId() {
        return task_id;
    }

    public void setTaskId(int task_id) {
        this.task_id = task_id;
    }

    public String getTaskName() {
        return task_name;
    }

    public void setTaskName(String task_name) {
        this.task_name = task_name;
    }

    public String getTaskDescription() {
        return task_description;
    }

    public void setTaskDescription(String task_description) {
        this.task_description = task_description;
    }

    public String getTaskPriority() {
        return task_priority;
    }

    public void setTaskPriority(String task_priority) {
        this.task_priority = task_priority;
    }

    public String getTaskDueDate() {
        return task_due_date;
    }

    public void setTaskDueDate(String task_due_date) {
        this.task_due_date = task_due_date;
    }

    public boolean is_completed() {
        return is_completed;
    }

    public void setComplete(boolean is_completed) {
        this.is_completed = is_completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", task_name='" + task_name + '\'' +
                ", task_description='" + task_description + '\'' +
                ", task_priority='" + task_priority + '\'' +
                ", task_due_date='" + task_due_date + '\'' +
                ", is_completed=" + is_completed +
                '}';
    }
}
