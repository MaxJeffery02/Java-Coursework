package application.usecases.queries.gettutorscourses;

import java.util.List;
import java.util.UUID;
import application.dtos.CourseDto;
import application.abstractions.Query;

public record GetTutorsCoursesQuery(List<UUID> courses) implements Query<List<CourseDto>> {
}