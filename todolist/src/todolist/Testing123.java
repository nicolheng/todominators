/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package todolist;

/**
 *
 * @author User
 */


imp


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import taskload.newpackage.taskloadd; // Import your taskloadd class

public class Testing123 extends Application {

    // Instance of taskloadd to load tasks
    private taskloadd taskLoader = new taskloadd();

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();

        // Top section: Search bar and sorting dropdown
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(10));
        
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Task");
        searchBar.setOnKeyReleased(event -> taskSearch(searchBar.getText()));

        ComboBox<String> sortDropdown = new ComboBox<>();
        sortDropdown.getItems().addAll("Due Date", "Priority", "Name (A-Z)", "Created Time");
        sortDropdown.setPromptText("Sort by:");
        sortDropdown.setOnAction(event -> sortTasks(sortDropdown.getValue()));

        topBar.getChildren().addAll(searchBar, sortDropdown);
        topBar.setAlignment(Pos.CENTER);

        TableView tableView = new TableView();
        
        TableColumn<Task, String> column1 = new TableColumn<>("Name");

        column1.setCellValueFactory(new PropertyValueFactory<>("Name"));


        TableColumn<Task, localDate> column2 = new TableColumn<>("Due date");

        column2.setCellValueFactory(new PropertyValueFactory<>("Due date"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        List a = new List();
        for (Task task: a) {
            tableView.getItems().add(task);
        }

        // Center section: Task display area
        VBox taskDisplayArea = new VBox(tableView);
        taskDisplayArea.setPadding(new Insets(10));
        taskDisplayArea.setSpacing(10);
        taskLoad(taskDisplayArea); // Call to load tasks into the display area

        // Bottom section: Buttons
        HBox bottomBar = new HBox(10);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER);

        Button analysisButton = new Button("Analysis");
        analysisButton.setOnAction(event -> analysis());

        Button taskButton = new Button("Task");
        taskButton.setOnAction(event -> taskLoad(taskDisplayArea));

        Button promptEmailButton = new Button("Prompt Email");
        promptEmailButton.setOnAction(event -> promptEmail());

        bottomBar.getChildren().addAll(analysisButton, taskButton, promptEmailButton);

        // Floating Add Button
        StackPane addButtonPane = new StackPane();
        Button addButton = new Button("+");
        addButton.setStyle("-fx-font-size: 20px; -fx-background-radius: 50%;");
        addButton.setOnAction(event -> taskAdd());
        addButtonPane.getChildren().add(addButton);
        StackPane.setAlignment(addButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(addButton, new Insets(10));

        // Assemble layout
        root.setTop(topBar);
        root.setCenter(taskDisplayArea);
        root.setBottom(bottomBar);
        root.getChildren().add(addButtonPane);

        // Scene setup
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Task Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Placeholder methods for functionality
    private void taskSearch(String query) {
        System.out.println("Search query: " + query);
        // Implement search functionality here
    }

    private void sortTasks(String criteria) {
        System.out.println("Sorting by: " + criteria);
        // Implement sorting functionality here
    }

    private void taskLoad(VBox taskDisplayArea) {
        taskDisplayArea.getChildren().clear();
        taskLoader.loadTasksFromDatabase(); // Load tasks from database
        System.out.println("Loading tasks...");

        // Loop through tasks and display them
        taskLoader.getTasks().forEach(task -> {
            // Create a label for each task
            Label taskLabel = new Label(task.getTaskName() + " - " + task.getTaskDueDate());
            taskDisplayArea.getChildren().add(taskLabel);
        });
    }

    private void taskAdd() {
        System.out.println("Add task button clicked.");
        // Implement task addition functionality here
    }

    private void analysis() {
        System.out.println("Analysis button clicked.");
        // Implement analysis functionality here
    }

    private void promptEmail() {
        System.out.println("Prompt email button clicked.");
        // Implement email prompting functionality here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
