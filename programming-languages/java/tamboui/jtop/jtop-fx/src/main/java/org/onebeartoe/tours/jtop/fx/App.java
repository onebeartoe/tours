package org.onebeartoe.tours.jtop.fx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Modern JavaFX entry point for JTop System Monitor.
 */
public class App extends Application {

    private JTopController controller;

    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("jtop.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        var scene = new Scene(root, 1120, 800);

        // Global keyboard shortcuts: [s] sort, [c] CPU view, [q] quit
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (controller != null) {
                controller.handleKeyPressed(event);
            }
        });

        stage.setTitle("JTop - System Monitor");
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            if (controller != null) {
                controller.shutdown();
            }
        });

        stage.show();
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.shutdown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
