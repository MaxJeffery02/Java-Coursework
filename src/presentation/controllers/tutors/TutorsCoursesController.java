package presentation.controllers.tutors;

import application.dtos.Claim;
import application.dtos.ClaimTypes;
import application.dtos.CourseDto;
import application.results.Result;
import application.usecases.queries.gettutorscourses.GetTutorsCoursesQuery;
import application.usecases.queries.gettutorscourses.GetTutorsCoursesQueryHandler;
import domain.entities.Course;
import infrastructure.services.SessionManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TutorsCoursesController {

    @FXML
    private TableView<CourseDto> tutorsTable;

    @FXML
    private TableColumn<CourseDto, String> courseNameColumn;

    @FXML
    private TableColumn<CourseDto, String> modulesColumn;

    // ObservableList to populate the table
    private final ObservableList<CourseDto> tutors = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the table columns
        courseNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().name()));
        modulesColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().modules()));

        // Add a button column
        addButtonToTable();

        // Populate the table with sample data
        loadCourses();
    }

    private void addButtonToTable() {
        Callback<TableColumn<CourseDto, Void>, TableCell<CourseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<CourseDto, Void> call(final TableColumn<CourseDto, Void> param) {
                return new TableCell<>() {
                    private final Button viewButton = new Button("View");

                    {
                        // Define the button action
                        viewButton.setOnAction((ActionEvent event) -> {
                            CourseDto selectedCourse = getTableView().getItems().get(getIndex());
                            viewCourseDetails(selectedCourse);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(viewButton);
                        }
                    }
                };
            }
        };
    }

    private void viewCourseDetails(CourseDto course) {
    }

    private void loadCourses() {

        SessionManager sessionManager = SessionManager.getInstance();

        List<UUID> courses = sessionManager.getClaims(ClaimTypes.COURSE_ID)
                .stream()
                .map((Claim claim) -> UUID.fromString(claim.value()))
                .collect(Collectors.toList());

        GetTutorsCoursesQuery query = new GetTutorsCoursesQuery(courses);

        GetTutorsCoursesQueryHandler handler = new GetTutorsCoursesQueryHandler();

        Result<List<CourseDto>> result = handler.handle(query);

        if (result.isSuccess()) {
            tutorsTable.setItems(FXCollections.observableArrayList(result.getData()));
        }
    }

}
