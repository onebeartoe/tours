package org.onebeartoe.tours.emojis;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FXMLController implements Initializable 
{
    @FXML
    private Label label;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
//        String bear = "🐻";
        
        // If the previous line doesn't show up in your editor,
        // you can comment it out and use this declaration instead: 
        String bear = "\ud83d\udc3b";
        
        Label bearLabel = new Label(bear);
        
        gridPane.add(bearLabel, 0, 0);
    }    
}
