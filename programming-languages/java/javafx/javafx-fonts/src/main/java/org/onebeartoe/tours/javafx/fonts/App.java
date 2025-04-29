package org.onebeartoe.tours.javafx.fonts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

/**
 * 
 */
public class App extends Application 
{
    private static Scene scene;
    
    @FXML
    private TextArea textArea;

    @Override
    public void start(Stage stage) throws IOException 
    {
        String fontPath = "/org/onebeartoe/tours/javafx/fonts/minecraft.ttf";
        
        InputStream instream = App.class.getResourceAsStream(fontPath);
        
        Font font = Font.loadFont(instream, 40);
        
        System.out.println("font name = " + font.getName());
        
        Parent parent = loadFXML("primary");

        scene = new Scene(parent, 640, 480);

        var fontSheet = "org/onebeartoe/tours/javafx/fonts/fonts.css";

        fontSheet = getClass().getResource(fontPath).toExternalForm();
        
        scene.getStylesheets().add(fontSheet);
        
        textArea.getStyleClass().add("title");
        
        stage.setScene(scene);

        stage.show();
    }

    static void setRoot(String fxml) throws IOException 
    {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) 
    {
        launch();
    }

}