package todolistgui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class EditTaskController {

    @FXML
    private TextField editTitleField;

    @FXML
    private TextField editDescriptionField;

    @FXML
    private DatePicker editDueDate;

    @FXML
    private TextField editCategoryField;

    @FXML
    private TextField editPriorityField;

    @FXML
    private Button deleteButton;
    private Button saveButton;
    private int taskId; // To identify the task being edited

    /**
     * Load task details from the database and display them in the text fields.
     *
     * @param taskId The ID of the task to edit.
     */
    public void loadTaskDetails(int taskId) {
    task task = Database1.getTaskById(taskId);  // Fetch the task by ID
    DatePicker editDueDate=new DatePicker();
    LocalDate dueDate=task.getDueDate();.
    if (task != null) {
        // Populate UI elements with the task data (e.g., TextFields)
        editTitleField.setText(task.getTitle());
        editDescriptionField.setText(task.getDescription());
        editDueDate.setValue(dueDate);
        editCategoryField.setText(task.getCategory());
        editPriorityField.setText(task.getPriority());
        // You can also display the completion status if needed
    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Task not found!", ButtonType.OK);
        alert.showAndWait();
    }
}
    /**
     * Save changes to the database.
     */
    @FXML
    private void handleSaveChanges() {
        // Retrieve updated values from the text fields
        String updatedTitle = editTitleField.getText().trim();
        String updatedDescription = editDescriptionField.getText().trim();
        String updatedDueDate = editDueDate.getValue().toString();
        String updatedCategory = editCategoryField.getText().trim();
        String updatedPriority = editPriorityField.getText().trim();

        // Validate input
        if (updatedTitle.isEmpty() || updatedDueDate==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Title and Due Date are required fields!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Update query
        String updateQuery = "UPDATE tasks SET task_name = ?, task_description = ?, task_due_date = ?, category = ?, priority = ? WHERE task_id = ?";

        try {
            boolean success = Database1.executeUpdate(
                    updateQuery,
                    updatedTitle,
                    updatedDescription,
                    updatedDueDate,
                    updatedCategory,
                    updatedPriority
                    
            );

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task updated successfully!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update the task. Please try again.", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving changes: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Handle cancel operation and close the edit window.
     */
    @FXML
    private void handleCancel() {
        editTitleField.getScene().getWindow().hide();
    }
}
