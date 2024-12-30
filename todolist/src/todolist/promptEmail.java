package todolist;
import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.scene.layout.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.*; 
import javafx.stage.Stage; 
import javafx.scene.control.Alert.AlertType; 
import java.time.LocalDate; 
public class promptEmail extends Application { 
  
    // launch the application 
    public TextInputDialog emailConfig(){
        // create a text input dialog 
        TextInputDialog td = new TextInputDialog(); 
  
        // setHeaderText 
        td.setHeaderText("enter your email");

        return td;

    }
    public void start(Stage s) 
    { 
        // set title for the stage 
        s.setTitle("creating textInput dialog"); 
  
        // create a tile pane 
        TilePane r = new TilePane(); 
  
         
  
        // create a button 
        Button d = new Button("click"); 
  
        // create a event handler 
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                TextInputDialog td = emailConfig();
                td.showAndWait();
                String email = td.getEditor().getText(); 
                Email.startEmail(email);

            } 
        }; 
  
        // set on action of event 
        d.setOnAction(event); 
  
        // add button and label 
        r.getChildren().add(d); 
  
        // create a scene 
        Scene sc = new Scene(r, 500, 300); 
  
        // set the scene 
        s.setScene(sc); 
  
        s.show(); 
    } 
  
    public static void main(String args[]) 
    { 
        // launch the application 
        launch(args); 
    } 
}