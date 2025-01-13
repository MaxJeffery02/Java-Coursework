package presentation.controllers.courses;

import application.results.Result;
import application.usecases.commands.createcourse.CreateCourseCommand;
import application.usecases.commands.createcourse.CreateCourseCommandHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import presentation.helpers.AlertHelper;
import java.util.UUID;

public class AddCourseController {

    @FXML
    private TextField nameField;

    public void handleCourseCreationAction(ActionEvent actionEvent) {

        CreateCourseCommand command = new CreateCourseCommand(nameField.getText());
        CreateCourseCommandHandler handler = new  CreateCourseCommandHandler();

        Result<UUID> result = handler.handle(command);

        // Handle the result of the creation
        if (result.isFailure()) {
            AlertHelper.show("Failed to enroll student", result.getMessageFromErrorResult(), Alert.AlertType.ERROR);
        } else {
            AlertHelper.show("Success", "Course created successfully!", Alert.AlertType.INFORMATION);
            clearForm(); // Clear the form after successful creation
        }
    }

    /**
     * Method to clear the form fields after creation.
     * This ensures the form is reset for the next creation.
     */
    private void clearForm() {
        nameField.clear();
    }
}
