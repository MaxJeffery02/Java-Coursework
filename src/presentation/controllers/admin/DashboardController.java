package presentation.controllers.admin;

import application.dtos.StudentDto;
import application.results.Result;
import application.usecases.queries.getallstudents.GetAllStudentsQuery;
import application.usecases.queries.getallstudents.GetAllStudentsQueryHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.Main;

import java.util.List;

public final class DashboardController {

    // Students Table
    @FXML
    private TableView<StudentDto> studentsTable;
    @FXML
    private TableColumn<StudentDto, String> studentFirstNameColumn;
    @FXML
    private TableColumn<StudentDto, String> studentLastNameColumn;
    @FXML
    private TableColumn<StudentDto, String> studentCourseColumn;

    @FXML
    private Button addStudentButton;

    @FXML
    public void initialize() {
        // Initialize Students Table
        studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentCourseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        studentsTable.setItems(getStudentsData());
    }

    private ObservableList<StudentDto> getStudentsData() {
        // Create the query and handler
        GetAllStudentsQuery query = new GetAllStudentsQuery();
        GetAllStudentsQueryHandler handler = new GetAllStudentsQueryHandler();

        // Handle the query and process the result
        Result<List<StudentDto>> result = handler.handle(query);

        if (result.isFailure()) {
            System.err.println("Error fetching students: " + result.getMessageFromErrorResult());
            return FXCollections.observableArrayList();
        }

        return FXCollections.observableArrayList(result.getData());
    }

    @FXML
    private void handleAddStudentAction() {
        Main.switchView("students/add");
    }
}