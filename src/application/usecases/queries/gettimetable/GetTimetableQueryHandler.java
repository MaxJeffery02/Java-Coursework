package application.usecases.queries.gettimetable;

import application.abstractions.QueryHandler;
import application.dtos.TimetableDto;
import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import domain.entities.Course;
import domain.entities.Student;
import domain.entities.Module;
import domain.enums.HttpStatusCode;
import domain.enums.StudentType;
import domain.enums.UserType;
import infrastructure.database.DbContext;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GetTimetableQueryHandler implements QueryHandler<GetTimetableQuery, TimetableDto> {

    private final DbContext DBCONTEXT;

    public GetTimetableQueryHandler() {
        this.DBCONTEXT = new DbContext();
    }

    @Override
    public Result<TimetableDto> handle(GetTimetableQuery query) {

        // Fetch student from the database
        Student student = (Student) DBCONTEXT.getUsers()
                .firstOrDefault(u -> u.getType() == UserType.STUDENT && u.getId().equals(query.userId()));

        if (student == null) {
            return new ErrorResult<>("Could not find student", HttpStatusCode.NOT_FOUND);
        }

        // Fetch student's course
        Course course = DBCONTEXT.getCourses()
                .firstOrDefault(c -> c.getId().equals(student.getCourse().getId()));

        if (course == null) {
            return new ErrorResult<>("Could not find student's course", HttpStatusCode.NOT_FOUND);
        }

        // Generate a timetable based on student type
        TimetableDto timetable = generateTimetable(student, course);

        return new SuccessResult<>(timetable);
    }

    private TimetableDto generateTimetable(Student student, Course course) {
        List<TimetableDto.TimetableEntry> timetableEntries = new ArrayList<>();
        List<Module> modules = course.getModules(); // Get course modules

        int studyDays = (student.getStudentType() == StudentType.APPRENTICE) ? 1 : 4; // Apprentice gets 1 day, others get 4
        int moduleCount = modules.size();

        int moduleIndex = 0;

        for (int i = 0; i < studyDays; i++) {
            DayOfWeek studyDay = (student.getStudentType() == StudentType.APPRENTICE)
                    ? DayOfWeek.THURSDAY
                    : getStudyDay(i);

            for (int j = 0; j < 2; j++) {
                Module module = modules.get(moduleIndex); // Get current module
                LocalTime startTime = (j == 0) ? LocalTime.of(9, 0) : LocalTime.of(13, 0);
                LocalTime endTime = (j == 0) ? LocalTime.of(12, 0) : LocalTime.of(16, 0);

                timetableEntries.add(new TimetableDto.TimetableEntry(
                        module.getName(),
                        studyDay,
                        startTime,
                        endTime
                ));

                moduleIndex = (moduleIndex + 1) % moduleCount;
            }
        }

        return new TimetableDto(student.getFirstName() + "'s Timetable", timetableEntries);
    }


    private DayOfWeek getStudyDay(int index) {
        // Assign study days: Monday to Thursday for full-time, any one day for apprentices
        DayOfWeek[] possibleDays = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY};
        return possibleDays[index % possibleDays.length]; // Loop through available days
    }
}
