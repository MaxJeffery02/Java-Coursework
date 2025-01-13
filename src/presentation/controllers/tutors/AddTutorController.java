package presentation.controllers.tutors;

import application.dtos.CourseDto;
import application.results.Result;
import application.usecases.commands.createtutor.CreateTutorCommand;
import application.usecases.commands.createtutor.CreateTutorCommandHandler;
import application.usecases.commands.enrollstudent.EnrollStudentCommand;
import application.usecases.commands.enrollstudent.EnrollStudentCommandHandler;
import application.usecases.queries.getallcourses.GetAllCoursesQuery;
import application.usecases.queries.getallcourses.GetAllCoursesQueryHandler;
import domain.enums.StudentType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import presentation.helpers.AlertHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AddTutorController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private ListView<CourseDto> courseListView;

    @FXML
    public void initialize() {
        // Set up multi-selection for the ListView
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Load and populate the courses
        List<CourseDto> courses = loadCourses();
        if (courses != null && !courses.isEmpty()) {
            courseListView.setItems(FXCollections.observableArrayList(courses));

            // Set a cell factory to display course names
            courseListView.setCellFactory(_ -> new ListCell<>() {
                @Override
                protected void updateItem(CourseDto course, boolean empty) {
                    super.updateItem(course, empty);
                    if (empty || course == null) {
                        setText(null);
                    } else {
                        setText(course.name());
                    }
                }
            });
        }
    }

    /**
     * Handle the creation button click event.
     * Validates the form, creates the command, and processes the creation.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleTutorCreationAction(ActionEvent event) {

        CreateTutorCommand command = mapCommand();

        // Use the CreateTutorCommandHandler to process the enrollment
        CreateTutorCommandHandler handler = new CreateTutorCommandHandler();
        Result<UUID> result = handler.handle(command);

        // Handle the result of the creation (this part can be expanded further)
        if (result.isFailure()) {
            AlertHelper.show("Failed to create tutor", result.getMessageFromErrorResult(), Alert.AlertType.ERROR);
        } else {
            AlertHelper.show("Success", "Tutor created successfully!", Alert.AlertType.INFORMATION);
            clearForm(); // Clear the form after successful creation
        }
    }

    /**
     * Maps the input fields to EnrollStudentCommand
     */
    private CreateTutorCommand mapCommand() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate dateOfBirth = dobPicker.getValue();
        List<UUID> selectedCourseIds = courseListView.getSelectionModel()
                .getSelectedItems()
                .stream()
                .map(CourseDto::id)
                .toList();

        // Create the EnrollStudentCommand with the collected data
        return new CreateTutorCommand(
                firstName,
                lastName,
                dateOfBirth,
                selectedCourseIds
        );
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
     * Method to clear the form fields after creation.
     * This ensures the form is reset for the next creation.
     */
    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        dobPicker.setValue(null);
        courseListView.getSelectionModel().clearSelection();
    }
}
