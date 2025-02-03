package presentation.controllers.students;

import application.dtos.ClaimTypes;
import application.dtos.TimetableDto;
import application.results.Result;
import application.usecases.queries.gettimetable.GetTimetableQuery;
import application.usecases.queries.gettimetable.GetTimetableQueryHandler;
import infrastructure.services.SessionManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import java.util.List;
import java.util.UUID;

public class TimetableController {

    // Timetable Table
    @FXML
    private TableView<TimetableDto.TimetableEntry> timetableTable;
    @FXML
    private TableColumn<TimetableDto.TimetableEntry, String> dayColumn;
    @FXML
    private TableColumn<TimetableDto.TimetableEntry, String> moduleColumn;
    @FXML
    private TableColumn<TimetableDto.TimetableEntry, String> startTimeColumn;
    @FXML
    private TableColumn<TimetableDto.TimetableEntry, String> endTimeColumn;
    @FXML
    private Label title;

    @FXML
    public void initialize() {
        // Initialize Table Columns
        dayColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().day().toString()));
        moduleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().moduleName()));
        startTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().startTime().toString()));
        endTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().endTime().toString()));


        SessionManager sessionManager = SessionManager.getInstance();
        UUID userId = UUID.fromString(sessionManager.getClaim(ClaimTypes.USER_ID).value());

        loadTimetableData(userId);
    }

    private void loadTimetableData(UUID studentId) {
        // Create the query and handler
        GetTimetableQuery query = new GetTimetableQuery(studentId);
        GetTimetableQueryHandler handler = new GetTimetableQueryHandler();

        // Handle the query and process the result
        Result<TimetableDto> result = handler.handle(query);

        if (result.isFailure()) {
            showAlert("Error", "Failed to load timetable: " + result.getMessageFromErrorResult());
            timetableTable.setItems(FXCollections.observableArrayList());
            return;
        }

        title.setText(result.getData().name());

        // Set the timetable data
        timetableTable.setItems(FXCollections.observableArrayList(result.getData().timetableEntries()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
