package application.usecases.queries.gettutorscourses;

import java.util.List;

import application.abstractions.Mapper;
import application.dtos.StudentDto;
import application.mappers.CourseDtoMapper;
import application.results.Result;
import application.dtos.CourseDto;
import application.results.SuccessResult;
import domain.entities.Course;
import domain.entities.User;
import infrastructure.database.DbContext;
import application.abstractions.QueryHandler;

public final class GetTutorsCoursesQueryHandler implements QueryHandler<GetTutorsCoursesQuery, List<CourseDto>> {

    private final DbContext DB_CONTEXT;
    private final Mapper<Course, CourseDto> COURSEMAPPER;

    public GetTutorsCoursesQueryHandler() {
        DB_CONTEXT = new DbContext();
        COURSEMAPPER = new CourseDtoMapper();
    }

    @Override
    public Result<List<CourseDto>> handle(GetTutorsCoursesQuery query) {

        List<CourseDto> courses = DB_CONTEXT.getCourses()
                .where(c -> query.courses().contains(c.getId()))
                .map(COURSEMAPPER::map);

        return new SuccessResult<>(courses);
    }
}