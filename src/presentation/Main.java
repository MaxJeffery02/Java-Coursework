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
import presentation.controllers.shared.LayoutController;

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

        // URL to the layout and the target view
        URL layoutURL = Main.class.getResource("../presentation/views/shared/layout.fxml");
        URL viewURL = Main.class.getResource("../presentation/views/" + viewName + ".fxml");

        if (layoutURL == null || viewURL == null) {
            viewURL = Main.class.getResource("../presentation/views/errors/notFound.fxml");
        }

        try {
            // Load the layout FXML
            FXMLLoader layoutLoader = new FXMLLoader(layoutURL);
            Parent layoutRoot = layoutLoader.load();

            // Load the target view FXML
            FXMLLoader viewLoader = new FXMLLoader(viewURL);
            Parent viewContent = viewLoader.load();

            // Inject the target view into the layout
            LayoutController layoutController = layoutLoader.getController();
            layoutController.setContent(viewContent);

            // Set the scene with the layout
            Scene scene = new Scene(layoutRoot, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle(viewName.toLowerCase());
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
