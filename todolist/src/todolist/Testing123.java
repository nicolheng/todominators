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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;

public class Testing123 extends Application {

    // Instance of taskloadd to load tasks

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

        column1.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<Task, LocalDate> column2 = new TableColumn<>("Due date");

        column2.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Task, String> column3 = new TableColumn<>("Priority");

        column3.setCellValueFactory(new PropertyValueFactory<>("priorityName"));

        TableColumn<Task, String> column4 = new TableColumn<>("Action");
        column4.setCellValueFactory(new PropertyValueFactory<>("button"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);                                                                            
        List tasks = new List();
        ObservableList<Task> tasksDisplay = FXCollections.observableArrayList(tasks.getList());

        tableView.setItems(tasksDisplay);

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

        Button taskButton = new Button("Add Task");
        taskButton.setOnAction(event -> showCreate());

        Button promptEmailButton = new Button("Prompt Email");
        promptEmailButton.setOnAction(event -> promptEmail());

        bottomBar.getChildren().addAll(analysisButton, taskButton, promptEmailButton);

        // Floating Add Button
        StackPane addButtonPane = new StackPane();
        Button addButton = new Button("+");
        addButton.setStyle("-fx-font-size: 20px; -fx-background-radius: 50%;");
        addButton.setOnAction(event -> showCreate());
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

    private void showCreate() {
        System.out.println("Add task button clicked.");
        // Implement task addition functionality here
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

    public static void main(String[] args) {
        launch(args);
    }
}
