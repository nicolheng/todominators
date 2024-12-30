package todolistgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML layout file and set the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editTask.fxml"));
            AnchorPane root = loader.load();
            EditTaskController controller = loader.getController();

            // Set the scene
            Scene scene = new Scene(root, 600, 600);
            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
