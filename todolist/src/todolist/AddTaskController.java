package todolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class AddTaskController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private MenuButton categoryMenu;

    @FXML
    private MenuItem homeworkCategory;

    @FXML
    private MenuItem personalCategory;

    @FXML
    private MenuItem workCategory;

    @FXML
    private MenuButton priorityMenu;

    @FXML
    private MenuItem lowPriority;

    @FXML
    private MenuItem mediumPriority;

    @FXML
    private MenuItem highPriority;
  
    
    @FXML
    private Button addTaskButton;
    private String selectedCategory;
    private String selectedPriority;

    @FXML
    public void initialize() {
        // Ensure menu items behave correctly
        homeworkCategory.setOnAction(event -> selectCategory("Homework"));
        personalCategory.setOnAction(event -> selectCategory("Personal"));
        workCategory.setOnAction(event -> selectCategory("Work"));

        lowPriority.setOnAction(event -> selectPriority("Low"));
        mediumPriority.setOnAction(event -> selectPriority("Medium"));
        highPriority.setOnAction(event -> selectPriority("High"));
    }

    private void selectCategory(String category) {
        selectedCategory = category;
        categoryMenu.setText(category); // Update the MenuButton text
    }

    private void selectPriority(String priority) {
        selectedPriority = priority;
        priorityMenu.setText(priority); // Update the MenuButton text
    }

    @FXML
    private void handleAddTask(ActionEvent event) {
    // Retrieve input values
    String title = titleField.getText();
    String description = descriptionField.getText();

    // Validate title
    if (title == null || title.trim().isEmpty()) {
        showAlert("Task title cannot be empty.");
        return;
    }

    // Validate description
    if (description == null || description.trim().isEmpty()) {
        showAlert("Task description cannot be empty.");
        return;
    }

    // Validate due date
    if (dueDatePicker.getValue() == null) {
        showAlert("Please select a due date.");
        return;
    }

    LocalDate dueDate = dueDatePicker.getValue();

    // Validate category and priority
    if (selectedCategory == null || selectedPriority == null) {
        showAlert("Please select a category and priority.");
        return;
    }

    // Database insertion logic
    Task.taskCreate(title, description, dueDate.toString(), selectedCategory, selectedPriority);
    clearFields();
}
        
    private void clearFields() {
        titleField.clear();
        descriptionField.clear();
        dueDatePicker.setValue(null);
        categoryMenu.setText("Select Category");
        priorityMenu.setText("Select Priority");
        selectedCategory = null;
        selectedPriority = null;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
