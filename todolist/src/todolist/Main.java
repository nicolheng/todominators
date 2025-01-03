/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package todolist;

/**
 *
 * @author User
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;

public class Main extends Application {

    public TableView tableView = new TableView();
    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Task");

        ObservableList<String> items = FXCollections.observableArrayList("Due Date Ascending", "Due Date Descending", "Priority (High to Low)", "Priority (Low to High)");
        ComboBox<String> sortDropdown = new ComboBox<>(items);
        sortDropdown.setItems(items);
        sortDropdown.setValue("Due Date Ascending");
        
        TableColumn<Task, String> column1 = new TableColumn<>("Name");

        column1.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<Task, LocalDate> column2 = new TableColumn<>("Due date");

        column2.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Task, String> column3 = new TableColumn<>("Priority");

        column3.setCellValueFactory(new PropertyValueFactory<>("priorityName"));

        TableColumn<Task, String> column4 = new TableColumn<>("Action");
        column4.setCellFactory(col -> new buttonCell());

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        
        ObservableList<Task> tasksDisplay = FXCollections.observableArrayList(List.listLoad());

        tableView.setItems(sortTasks("Due Date Ascending"));

        // Top section: Search bar and sorting dropdown
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(10));

        searchBar.setOnKeyReleased(event -> {
            if (searchBar.getText() != null){
                ObservableList<Task> searchResult = taskSearch(searchBar.getText());
                tableView.setItems(searchResult);
            }
        });
        sortDropdown.setOnAction(event -> tableView.setItems(sortTasks(sortDropdown.getValue())));

        topBar.getChildren().addAll(searchBar, sortDropdown);
        topBar.setAlignment(Pos.CENTER);

        // Center section: Task display area
        VBox taskDisplayArea = new VBox(tableView);
        taskDisplayArea.setPadding(new Insets(10));
        taskDisplayArea.setSpacing(10);

        // Bottom section: Buttons
        HBox bottomBar = new HBox(10);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER);

        Button analysisButton = new Button("Analytics");
        analysisButton.setOnAction(event -> showAnalytics());

        Button addTaskButton = new Button("Add Task");
        addTaskButton.setOnAction(event -> showCreate(primaryStage, tableView));

        Button promptEmailButton = new Button("Prompt Email");
        promptEmailButton.setOnAction(event -> promptEmail());

        bottomBar.getChildren().addAll(analysisButton, addTaskButton, promptEmailButton);

        // Assemble layout
        root.setTop(topBar);
        root.setCenter(taskDisplayArea);
        root.setBottom(bottomBar);

        // Scene setup
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Task Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Placeholder methods for functionality
    private ObservableList<Task> taskSearch(String query) {
        System.out.println("Search query: " + query);
        // Implement search functionality here
        ArrayList <Task> searched = List.listSearch(query);
        ObservableList<Task> tasksDisplay = FXCollections.observableArrayList(searched);
        return tasksDisplay;
    }

    private void reload(TableView tableView){
        tableView.setItems(FXCollections.observableArrayList(List.listLoad()));
    }

    private ObservableList<Task> sortTasks(String criteria) {
        System.out.println("Sorting by: " + criteria);
        // Implement sorting functionality here
        int choiceInt;
        if (criteria.equals("Due Date Ascending"))
            choiceInt = 1;
        else if (criteria.equals("Due Date Descending"))
            choiceInt = 2;
        else if (criteria.equals("Priority (High to Low)"))
            choiceInt = 3;
        else
            choiceInt = 4;
        ArrayList <Task> sorted = List.listSort(choiceInt);
        ObservableList<Task> tasksDisplay = FXCollections.observableArrayList(sorted);
        return tasksDisplay;

    }
    class buttonCell extends javafx.scene.control.TableCell<Task, String> {
        private final Button button = new Button("Detail");

        public buttonCell() {
            button.setOnAction(event -> {
                Task task = getTableView().getItems().get(getIndex());
                showEdit(task, event);
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(button);
            }
        }
    }

    private void showEdit(Task task, ActionEvent event1) {
        BorderPane root = new BorderPane();
        System.out.println("Edit task button clicked.");
    
        // Create UI elements for editing the task
        Label titleLabel = new Label("Enter Task Title:");
        TextField titleField = new TextField(task.getName());
        titleField.setMaxWidth(300);
        VBox.setMargin(titleField, new Insets(0, 0, 10, 20));
    
        Label descriptionLabel = new Label("Enter Task Description:");
        TextField descriptionField = new TextField(task.getDescription());
        descriptionField.setMaxWidth(300);
        VBox.setMargin(descriptionField, new Insets(0, 0, 10, 20));
    
        Label dueDateLabel = new Label("Enter Due Date:");
        DatePicker dueDatePicker = new DatePicker(task.getDueDate());
        dueDatePicker.setMaxWidth(300);
        VBox.setMargin(dueDatePicker, new Insets(0, 0, 10, 20));
    
        Label categoryLabel = new Label("Select Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Homework", "Personal", "Work");
        categoryComboBox.setValue(task.getCategory());
        categoryComboBox.setMaxWidth(300);
        VBox.setMargin(categoryComboBox, new Insets(0, 0, 10, 20));
    
        Label priorityLabel = new Label("Select Priority:");
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Low", "Medium", "High");
        priorityComboBox.setValue(task.getPriorityName());
        priorityComboBox.setMaxWidth(300);
        VBox.setMargin(priorityComboBox, new Insets(0, 0, 10, 20));
    
        Label recurringLabel = new Label("Select Recurring:");
        ComboBox<String> recurringComboBox = new ComboBox<>();
        recurringComboBox.getItems().addAll("None", "Daily", "Weekly", "Monthly");
        recurringComboBox.setValue(task.getRecurringName());
        recurringComboBox.setMaxWidth(300);
        VBox.setMargin(recurringComboBox, new Insets(0, 0, 10, 20));
    
        // Set task dependency using ComboBox (Modified)
        Label dependencyLabel = new Label("Select Task Dependency:");
        ComboBox<Task> dependencyComboBox = new ComboBox<>();
        ObservableList<Task> tasks = FXCollections.observableArrayList(List.listLoad());  // Get list of tasks from the database or any data source
        // Populate ComboBox with task objects
        dependencyComboBox.getItems().addAll(tasks);
        dependencyComboBox.setPromptText("Select Task to Depend On");
        String query = "SELECT depends_on_task_id FROM task_dependencies WHERE task_id = "+task.getID();
        try (Connection conn = Database.getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                System.out.println("yes");
                int id = rs.getInt("depends_on_task_id");
                System.out.println(id);
                for (Task x: tasks){
                    System.out.println(x.getID());
                    if (x.getID() == id) {
                        System.out.println("yes");
                        dependencyComboBox.setValue(x);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurs. " + e.getMessage());
        }
        dependencyComboBox.setMaxWidth(300);
        VBox.setMargin(dependencyComboBox, new Insets(0, 0, 10, 20));

        // Bottom buttons
        HBox bottomBar = new HBox(10);
        bottomBar.setAlignment(Pos.CENTER);
        Button deleteButton = new Button("Delete");
        VBox.setMargin(deleteButton, new Insets(20, 0, 0, 300));
        Button saveButton = new Button("Save Changes");
        VBox.setMargin(saveButton, new Insets(20, 0, 0, 300));
        Button completeButton = new Button("Complete");
        VBox.setMargin(saveButton, new Insets(20, 0, 0, 300));
        bottomBar.getChildren().addAll(deleteButton, saveButton, completeButton);
        
        // Layout setup
        VBox layout = new VBox(10, titleLabel, titleField, descriptionLabel, descriptionField,
                dueDateLabel, dueDatePicker, categoryLabel, categoryComboBox,
                priorityLabel, priorityComboBox, recurringLabel, recurringComboBox, dependencyLabel, dependencyComboBox, bottomBar);
        layout.setPadding(new Insets(15));

        // Event handler for Save button
        saveButton.setOnAction(event -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            String selectedCategory = categoryComboBox.getValue();
            String selectedPriority = priorityComboBox.getValue();
            String selectedRecurring = recurringComboBox.getValue();
    
            // Validation
            if (title == null || title.trim().isEmpty()) {
                showAlert("Task title cannot be empty.");
                return;
            }
            if (description == null || description.trim().isEmpty()) {
                showAlert("Task description cannot be empty.");
                return;
            }
            if (dueDate == null) {
                showAlert("Please select a due date.");
                return;
            }
            if (selectedCategory == null) {
                showAlert("Please select a category.");
                return;
            }
            if (selectedPriority == null) {
                showAlert("Please select a priority.");
                return;
            }
    
            // Update task and refresh TableView
            task.taskEdit();
    
            // Close the window
            ((Stage) saveButton.getScene().getWindow()).close();
        });
    
        // Event handler for Delete button
        deleteButton.setOnAction(event -> {
            task.taskDelete();
            tableView.setItems(FXCollections.observableArrayList(List.listLoad()));
            ((Stage) deleteButton.getScene().getWindow()).close();
        });

        completeButton.setOnAction(event -> {
            task.taskComplete(task.getID());
            tableView.setItems(FXCollections.observableArrayList(List.listLoad()));
            ((Stage) completeButton.getScene().getWindow()).close();
        });
    
        // Scene and Stage setup
        Scene scene = new Scene(layout, 400, 600);
        Stage editStage = new Stage();
        editStage.setTitle("Edit Task");
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.initOwner(((Node)event1.getSource()).getScene().getWindow());
        editStage.showAndWait();
    }

    private void showCreate(Stage parentStage, TableView<Task> tableView) {
        System.out.println("Add task button clicked.");
        
        // Create UI elements for the task creation form
        Label titleLabel = new Label("Enter Task Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter task title");
        titleField.setMaxWidth(300);
        VBox.setMargin(titleField, new Insets(0, 0, 10, 20));
    
        Label descriptionLabel = new Label("Enter Task Description:");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter task description");
        descriptionField.setMaxWidth(300);
        VBox.setMargin(descriptionField, new Insets(0, 0, 10, 20));
    
        Label duedateLabel = new Label("Enter Due Date:");
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Select due date");
        dueDatePicker.setMaxWidth(300);
        VBox.setMargin(dueDatePicker, new Insets(0, 0, 10, 20));
    
        // Category selection using ComboBox
        Label categoryLabel = new Label("Select Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Homework", "Personal", "Work");
        categoryComboBox.setPromptText("Select Category");
        categoryComboBox.setMaxWidth(300);
        VBox.setMargin(categoryComboBox, new Insets(0, 0, 10, 20));
    
        // Priority selection using ComboBox
        Label priorityLabel = new Label("Select Priority:");
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Low", "Medium", "High");
        priorityComboBox.setPromptText("Select Priority");
        priorityComboBox.setMaxWidth(300);
        VBox.setMargin(priorityComboBox, new Insets(0, 0, 10, 20));
    
        // Recurring task selection using ComboBox
        Label recurringLabel = new Label("Select Recurring:");
        ComboBox<String> recurringComboBox = new ComboBox<>();
        recurringComboBox.getItems().addAll("None", "Daily", "Weekly", "Monthly");
        recurringComboBox.setPromptText("Select Recurring");
        recurringComboBox.setMaxWidth(300);
        VBox.setMargin(recurringComboBox, new Insets(0, 0, 10, 20));
    
        // Set task dependency using ComboBox (Modified)
        Label dependencyLabel = new Label("Select Task Dependency:");
        ComboBox<Task> dependencyComboBox = new ComboBox<>();
        ObservableList<Task> tasks = FXCollections.observableArrayList(List.listLoad());  // Get list of tasks from the database or any data source
    

        // Populate ComboBox with task objects
        dependencyComboBox.getItems().addAll(tasks);
        dependencyComboBox.setPromptText("Select Task to Depend On");
        dependencyComboBox.setMaxWidth(300);
        VBox.setMargin(dependencyComboBox, new Insets(0, 0, 10, 20));
    
        // Add Task button
        Button addTaskButton = new Button("Add Task");
        VBox.setMargin(addTaskButton, new Insets(20, 0, 0, 300)); // Fixed margin to center the button
    
        // Event handler for the Add Task button
        addTaskButton.setOnAction(event -> {
            // Retrieve input values
            String title = titleField.getText();
            String description = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            String selectedCategory = categoryComboBox.getValue();
            String selectedPriority = priorityComboBox.getValue();
            String selectedRecurring = recurringComboBox.getValue();
            Task selectedDependencyTask = dependencyComboBox.getValue(); // This is the selected Task object
            String selectedDependencyTaskName = selectedDependencyTask != null ? selectedDependencyTask.getName() : "None";
    
            // Validate input
            if (title == null || title.trim().isEmpty()) {
                showAlert("Task title cannot be empty.");
                return;
            }
    
            if (description == null || description.trim().isEmpty()) {
                showAlert("Task description cannot be empty.");
                return;
            }
    
            if (dueDate == null) {
                showAlert("Please select a due date.");
                return;
            }
    
            if (selectedCategory == null) {
                showAlert("Please select a category.");
                return;
            }
    
            if (selectedPriority == null) {
                showAlert("Please select a priority.");
                return;
            }
    
            if (selectedRecurring == null) {
                selectedRecurring = "None"; // Default value if recurring is not selected
            }


            // Call the taskCreate method to create the task
            Task.taskCreate(title, description, dueDate, selectedCategory, selectedPriority, selectedRecurring);
            
            List newTask = new List();
            // After task creation, get the ID of the newly created task
            int newTaskId = newTask.getTaskId(title);
    
            // If a dependency is selected and it is not "None", create the dependency
            if (selectedDependencyTask != null) {
                int selectedDependencyId = newTask.getTaskId(selectedDependencyTaskName);
                Task taskWithDependency = new Task();
                taskWithDependency.taskDependency(newTaskId, selectedDependencyId); // Create dependency
            }
    
            // Refresh the parent TableView
            tableView.setItems(FXCollections.observableArrayList(List.listLoad()));
    
            // Close the window after adding the task
            Stage stage = (Stage) addTaskButton.getScene().getWindow();
            stage.close();
        });
    
        // Layout setup
        VBox layout = new VBox(10,
            titleLabel,
            titleField,
            descriptionLabel,
            descriptionField,
            duedateLabel,
            dueDatePicker,
            categoryLabel, categoryComboBox,
            priorityLabel, priorityComboBox,
            recurringLabel, recurringComboBox,
            dependencyLabel, dependencyComboBox,
            addTaskButton);
        layout.setPadding(new Insets(15));
    
        // Scene and Stage setup
        Scene scene = new Scene(layout, 400, 650);
        Stage addTaskStage = new Stage();
        addTaskStage.setTitle("Add Task");
        addTaskStage.setScene(scene);
        addTaskStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
        addTaskStage.initOwner(parentStage); // Set parent stage
        addTaskStage.showAndWait();
    }
    
    
    
    private void showAnalytics() {
        System.out.println("Analysis button clicked.");
        Stage analytics = new Stage();
        String output = List.listAnalytics();
        Label label = new Label(output);
        StackPane analyticsLayout = new StackPane(label);
        analyticsLayout.setStyle("-fx-padding: 20px;");
        Scene analyticsScene = new Scene(analyticsLayout, 250, 300);
        analytics.setTitle("Analytics");
        analytics.setScene(analyticsScene);
        analytics.showAndWait();
    }

    private void promptEmail() {
        System.out.println("Prompt email button clicked.");
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("enter your email");
        td.showAndWait();
        String email = td.getEditor().getText();
        Email.startEmail(email);
        // Implement email prompting functionality here
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
