package application.usecases.queries.getallcourses;

import application.abstractions.QueryHandler;
import application.dtos.CourseDto;
import application.results.Result;
import application.results.SuccessResult;
import infrastructure.database.DbContext;

import java.util.List;

public final class GetAllCoursesQueryHandler implements QueryHandler<GetAllCoursesQuery, List<CourseDto>> {

    private final DbContext DB_CONTEXT;

    public GetAllCoursesQueryHandler() {
        DB_CONTEXT = new DbContext();
    }

    @Override
    public Result<List<CourseDto>> handle(GetAllCoursesQuery query) {

        List<CourseDto> courses = DB_CONTEXT.getCourses()
                .map(c -> new CourseDto(c.getId(), c.getName()));

        return new SuccessResult<>(courses);
    }
}
