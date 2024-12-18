package application.usecases.queries.getallstudents;

import java.util.List;
import application.dtos.StudentDto;
import application.abstractions.Query;

public record GetAllStudentsQuery() implements Query<List<StudentDto>> {
}