package presentation.controllers;

import application.dtos.StudentDto;
import application.results.Result;
import application.usecases.queries.getstudents.GetStudentsQuery;
import application.usecases.queries.getstudents.GetStudentsQueryHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminDashboardController {

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
    public void initialize() {
        // Initialize Students Table
        studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentCourseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        studentsTable.setItems(getStudentsData());
    }

    private ObservableList<StudentDto> getStudentsData() {
        // Create the query and handler
        GetStudentsQuery query = new GetStudentsQuery();
        GetStudentsQueryHandler handler = new GetStudentsQueryHandler();

        // Handle the query and process the result
        Result<List<StudentDto>> result = handler.handle(query);

        if (result.isFailure()) {
            System.err.println("Error fetching students: " + result.getMessageFromErrorResult());
            return FXCollections.observableArrayList();
        }

        return FXCollections.observableArrayList(result.getData());
    }
}