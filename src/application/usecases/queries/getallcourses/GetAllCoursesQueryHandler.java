package application.usecases.queries.getallcourses;

import application.abstractions.Mapper;
import application.abstractions.QueryHandler;
import application.dtos.CourseDto;
import application.mappers.CourseDtoMapper;
import application.results.Result;
import application.results.SuccessResult;
import domain.entities.Course;
import infrastructure.database.DbContext;

import java.util.List;

public final class GetAllCoursesQueryHandler implements QueryHandler<GetAllCoursesQuery, List<CourseDto>> {

    private final DbContext DB_CONTEXT;
    private final Mapper<Course, CourseDto> COURSEMAPPER;

    public GetAllCoursesQueryHandler() {
        DB_CONTEXT = new DbContext();
        COURSEMAPPER = new CourseDtoMapper();
    }

    @Override
    public Result<List<CourseDto>> handle(GetAllCoursesQuery query) {

        List<CourseDto> courses = DB_CONTEXT.getCourses()
                .map(COURSEMAPPER::map);

        return new SuccessResult<>(courses);
    }
}
