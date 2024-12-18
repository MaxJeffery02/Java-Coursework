package presentation;

import java.io.IOException;
import java.net.URL;

import application.exceptions.ViewNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import javafx.application.Application;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    /**
     * The primary stage of the application, used to set scenes and manage the main window.
     */
    private static Stage primaryStage;

    /**
     * This method is called when the application starts. It initializes the primary stage and switches to the login view.
     * @param stage The main stage provided by the JavaFX runtime.
     * @throws Exception If there is an issue loading the view.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Set the primaryStage to the provided stage
        primaryStage = stage;

        // Switch to the login view on application startup
        switchView("authentication/login");
    }

    /**
     * Switches the current view by loading the specified FXML file and setting it as the scene.
     * @param viewName The name of the FXML view to load.
     * @throws ViewNotFoundException If the specified view cannot be found.
     */
    public static void switchView(String viewName) throws ViewNotFoundException {

        // Construct the URL to the FXML file based on the viewName parameter
        URL view = Main.class.getResource("../presentation/views/" + viewName + ".fxml");

        // If the FXML file doesn't exist, throw a ViewNotFoundException
        if(view == null) view = Main.class.getResource("../presentation/views/errors/notFound.fxml");

        try {
            // Load the FXML file into a Parent object (the root of the scene graph)
            assert view != null;
            Parent root = FXMLLoader.load(view);

            // Create a new Scene with the loaded root element and set the desired size
            Scene scene = new Scene(root, 600, 400);

            // Set the scene on the primary stage and set the window's title
            primaryStage.setScene(scene);
            primaryStage.setTitle(viewName.toLowerCase());

            // Show the primary stage (window) with the new scene
            primaryStage.show();

        } catch (IOException e) {
            return;
        }
    }

    /**
     * The main method is the entry point for the JavaFX application.
     * It launches the JavaFX application by calling the launch() method.
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
