package application.usecases.queries.getallcourses;

import application.abstractions.Query;
import application.dtos.CourseDto;

import java.util.List;

public record GetAllCoursesQuery() implements Query<List<CourseDto>> {
}