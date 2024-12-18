package presentation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.UUID;

import application.exceptions.ViewNotFoundException;
import application.usecases.commands.enroll.EnrollStudentCommand;
import application.usecases.commands.enroll.EnrollStudentCommandHandler;
import domain.enums.StudentType;
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

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        switchView("login");
    }

    public static void switchView(String viewName) throws ViewNotFoundException {

        // Find the fxml file
        URL view = Main.class.getResource("../presentation/views/" + viewName +".fxml");

        // Throw homePage not found exception if the homePage does not exist
        if(view == null) throw new ViewNotFoundException(viewName);

        try {
            // Load homePage in the root
            Parent root = FXMLLoader.load(view);

            // Initiate a new Scene object
            Scene scene = new Scene(root, 400, 600);

            // Set the scene as the primary stage
            primaryStage.setScene(scene);
            primaryStage.setTitle(viewName.substring(0, 1).toUpperCase() + viewName.substring(1).toLowerCase());
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void makeText(String toastMsg)
    {
        Stage toastStage=new Stage();
        toastStage.initOwner(primaryStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Poppins", 40));
        text.setFill(Color.RED);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(500), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
        {
            new Thread(() -> {
                try
                {
                    Thread.sleep(3500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(500), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                fadeOutTimeline.play();
            }).start();
        });
        fadeInTimeline.play();
    }
}