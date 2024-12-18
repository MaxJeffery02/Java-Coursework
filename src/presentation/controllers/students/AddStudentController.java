package presentation.controllers.students;

import application.results.Result;
import application.usecases.commands.enrollstudent.EnrollStudentCommand;
import application.usecases.commands.enrollstudent.EnrollStudentCommandHandler;
import application.usecases.queries.getallcourses.GetAllCoursesQuery;
import application.usecases.queries.getallcourses.GetAllCoursesQueryHandler;
import domain.enums.StudentType;
import application.dtos.CourseDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import presentation.helpers.AlertHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AddStudentController {

    /** FXML fields for user input in the student enrollment form */
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private ComboBox<CourseDto> courseComboBox;

    @FXML
    private TextField employerField;

    @FXML
    private TextField countryField;

    @FXML
    private ComboBox<StudentType> studentTypeCombo;

    @FXML
    private Button enrollButton;

    /** HBox containers for dynamically showing/hiding fields based on student type */
    @FXML
    private HBox employerBox;

    @FXML
    private HBox countryBox;

    /**
     * Initialize the controller, setting up ComboBoxes and other UI elements.
     * This method runs automatically when the FXML file is loaded.
     */
    @FXML
    public void initialize() {
        // Populate the ComboBox with StudentType enum values (APPRENTICE, FOREIGN_EXCHANGE, etc.)
        studentTypeCombo.getItems().setAll(StudentType.values());

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

        // Add listener to the studentTypeCombo to show/hide employer or country fields based on student type
        studentTypeCombo.valueProperty()
                .addListener((observable, oldValue, newValue) -> handleStudentTypeChange(newValue));
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
     * Handle the changes to the StudentType combo box.
     * Dynamically show or hide fields based on selected student type.
     * @param newStudentType The newly selected StudentType.
     */
    private void handleStudentTypeChange(StudentType newStudentType) {
        if (newStudentType == StudentType.APPRENTICE) {
            employerBox.setVisible(true);  // Show employer field if the student is an apprentice
            countryBox.setVisible(false);  // Hide country field
        } else if (newStudentType == StudentType.FOREIGN_EXCHANGE) {
            employerBox.setVisible(false);  // Hide employer field
            countryBox.setVisible(true);   // Show country field for foreign exchange students
        } else {
            employerBox.setVisible(false);  // Hide both fields if student type is neither
            countryBox.setVisible(false);
        }
    }

    /**
     * Handle the enrollment button click event.
     * Validates the form, creates the command, and processes the enrollment.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleEnrollAction(ActionEvent event) {

        EnrollStudentCommand command = mapCommand();

        // Use the EnrollStudentCommandHandler to process the enrollment
        EnrollStudentCommandHandler handler = new EnrollStudentCommandHandler();
        Result<UUID> result = handler.handle(command);

        // Handle the result of the enrollment (this part can be expanded further)
        if (result.isFailure()) {
            AlertHelper.show("Failed to enroll student", result.getMessageFromErrorResult(), Alert.AlertType.ERROR);
        } else {
            AlertHelper.show("Success", "Student enrolled successfully!", Alert.AlertType.INFORMATION);
            clearForm(); // Clear the form after successful enrollment
        }
    }

    /**
     * Maps the input fields to EnrollStudentCommand
     */
    private EnrollStudentCommand mapCommand() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        LocalDate dateOfBirth = dobPicker.getValue();
        CourseDto selectedCourse = courseComboBox.getValue();
        String employer = employerField.getText();
        String country = countryField.getText();
        StudentType studentType = studentTypeCombo.getValue();

        // Create the EnrollStudentCommand with the collected data
        return new EnrollStudentCommand(
                firstName,
                lastName,
                password,
                dateOfBirth,
                selectedCourse.id(),
                employer,
                country,
                studentType
        );
    }

    /**
     * Method to clear the form fields after enrollment.
     * This ensures the form is reset for the next enrollment.
     */
    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();
        dobPicker.setValue(null);
        courseComboBox.getSelectionModel().clearSelection();
        employerField.clear();
        countryField.clear();
        studentTypeCombo.getSelectionModel().clearSelection();
    }
}
