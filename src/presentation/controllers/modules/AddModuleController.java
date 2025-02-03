package presentation.controllers.modules;

import application.dtos.CourseDto;
import application.results.Result;
import application.usecases.commands.addmodule.AddModuleCommand;
import application.usecases.commands.addmodule.AddModuleCommandHandler;
import application.usecases.commands.enrollstudent.EnrollStudentCommand;
import application.usecases.commands.enrollstudent.EnrollStudentCommandHandler;
import application.usecases.queries.getallcourses.GetAllCoursesQuery;
import application.usecases.queries.getallcourses.GetAllCoursesQueryHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import presentation.helpers.AlertHelper;

import java.util.List;
import java.util.UUID;

public class AddModuleController {

    @FXML
    private ComboBox<CourseDto> courseComboBox;

    @FXML
    private TextField moduleNameField;

    @FXML
    public void initialize() {
        // Load the list of courses from the backend or a data source and populate the courseComboBox
        List<CourseDto> courses = loadCourses();
        courseComboBox.getItems().setAll(courses);

        // Set up custom cell rendering for course names in the ComboBox
        courseComboBox.setCellFactory(courseComboBox -> {
            return new ListCell<>() {
                @Override
                protected void updateItem(CourseDto item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.name());
                }
            };
        });
        courseComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CourseDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.name());
            }
        });
    }

    /**
     * Load a list of courses from the backend or a data source.
     * @return A list of CourseDto objects.
     */
    private List<CourseDto> loadCourses() {
        // Use GetAllCoursesQueryHandler to fetch available courses
        GetAllCoursesQueryHandler handler = new GetAllCoursesQueryHandler();
        Result<List<CourseDto>> result = handler.handle(new GetAllCoursesQuery());

        // If the result is a failure, return an empty list
        if(result.isFailure()){
            return List.of();
        }

        // Return the list of courses if the result is successful
        return result.getData();
    }

    /**
     * Handle the enrollment button click event.
     * Validates the form, creates the command, and processes the enrollment.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleAddModuleAction(ActionEvent event) {

        AddModuleCommand command = new AddModuleCommand(
                courseComboBox.getValue().id(),
                moduleNameField.getText());

        // Use the AddModuleCommandHandler to process the creation
        AddModuleCommandHandler handler = new AddModuleCommandHandler();
        Result<UUID> result = handler.handle(command);

        // Handle the result of the adding
        if (result.isFailure()) {
            AlertHelper.show("Failed to add module", result.getMessageFromErrorResult(), Alert.AlertType.ERROR);
        } else {
            AlertHelper.show("Success", "Module added", Alert.AlertType.INFORMATION);
            clearForm(); // Clear the form after successful creation
        }
    }

    /**
     * Method to clear the form fields after creation.
     * This ensures the form is reset for the next creation.
     */
    private void clearForm() {
        courseComboBox.getSelectionModel().clearSelection();
    }
}
