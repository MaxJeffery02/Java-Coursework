package presentation.controllers.tutors;

import application.results.Result;
import application.usecases.queries.getalltutors.GetAllTutorsQuery;
import application.usecases.queries.getalltutors.GetAllTutorsQueryHandler;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import application.dtos.TutorDto;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.util.List;

public class TutorListController {

    // Tutors Table
    @FXML
    private TableView<TutorDto> tutorsTable;
    @FXML
    private TableColumn<TutorDto, String> tutorFirstNameColumn;
    @FXML
    private TableColumn<TutorDto, String> tutorLastNameColumn;
    @FXML
    private TableColumn<TutorDto, String> tutorEmailColumn;
    @FXML
    private TableColumn<TutorDto, String> tutorCoursesTaughtColumn;

    @FXML
    public void initialize() {
        // Initialize Tutors Table
        tutorFirstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().firstName()));
        tutorLastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().lastName()));
        tutorEmailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().email()));
        tutorCoursesTaughtColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().coursesTaught()));

        tutorsTable.setItems(getTutorsData());
    }

    private ObservableList<TutorDto> getTutorsData() {
        // Create the query and handler
        GetAllTutorsQuery query = new GetAllTutorsQuery();
        GetAllTutorsQueryHandler handler = new GetAllTutorsQueryHandler();

        // Handle the query and process the result
        Result<List<TutorDto>> result = handler.handle(query);

        if (result.isFailure()) {
            return FXCollections.observableArrayList();
        }

        return FXCollections.observableArrayList(result.getData());
    }

}
