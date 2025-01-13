package presentation.controllers.students;

import application.dtos.StudentDto;
import application.results.Result;
import application.usecases.queries.getallstudents.GetAllStudentsQuery;
import application.usecases.queries.getallstudents.GetAllStudentsQueryHandler;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class StudentListController {

    // Students Table
    @FXML
    private TableView<StudentDto> studentsTable;
    @FXML
    private TableColumn<StudentDto, String> studentFirstNameColumn;
    @FXML
    private TableColumn<StudentDto, String> studentLastNameColumn;
    @FXML
    private TableColumn<StudentDto, String> studentEmailColumn;
    @FXML
    private TableColumn<StudentDto, String> studentCourseColumn;
    @FXML
    private TableColumn<StudentDto, String> studentTypeColumn;
    @FXML
    private TableColumn<StudentDto, String> studentEmployerColumn;
    @FXML
    private TableColumn<StudentDto, String> studentCountryColumn;

    @FXML
    public void initialize() {
        // Initialize Students Table
        studentFirstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().firstName()));
        studentLastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().lastName()));
        studentCourseColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().course()));
        studentTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().type()));
        studentCountryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().country()));
        studentEmployerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().employer()));
        studentEmailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().email()));

        studentsTable.setItems(getStudentsData());
    }

    private ObservableList<StudentDto> getStudentsData() {
        // Create the query and handler
        GetAllStudentsQuery query = new GetAllStudentsQuery();
        GetAllStudentsQueryHandler handler = new GetAllStudentsQueryHandler();

        // Handle the query and process the result
        Result<List<StudentDto>> result = handler.handle(query);

        if (result.isFailure()) {
            return FXCollections.observableArrayList();
        }

        return FXCollections.observableArrayList(result.getData());
    }
}