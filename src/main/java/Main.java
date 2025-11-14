package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main application class untuk Sistem Manajemen Salon
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create root pane
            StackPane root = new StackPane();
            
            // Add welcome label
            Label welcomeLabel = new Label("Selamat datang di Sistem Manajemen Salon");
            root.getChildren().add(welcomeLabel);
            
            // Create scene
            Scene scene = new Scene(root, 800, 600);
            
            // Setup stage
            primaryStage.setTitle("Sistem Manajemen Salon");
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
