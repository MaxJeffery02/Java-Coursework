package application.usecases.queries.getstudents;

import java.util.List;
import application.dtos.StudentDto;
import application.abstractions.Query;

public record GetStudentsQuery() implements Query<List<StudentDto>> {
}