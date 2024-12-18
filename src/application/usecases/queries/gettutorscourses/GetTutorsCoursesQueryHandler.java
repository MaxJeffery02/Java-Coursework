package application.usecases.queries.gettutorscourses;

import java.util.List;
import application.results.Result;
import application.dtos.CourseDto;
import application.results.SuccessResult;
import infrastructure.database.DbContext;
import application.abstractions.QueryHandler;

public final class GetTutorsCoursesQueryHandler implements QueryHandler<GetTutorsCoursesQuery, List<CourseDto>> {

    private final DbContext DB_CONTEXT;

    public GetTutorsCoursesQueryHandler() {
        DB_CONTEXT = new DbContext();
    }

    @Override
    public Result<List<CourseDto>> handle(GetTutorsCoursesQuery query) {

        List<CourseDto> courses = DB_CONTEXT.getCourses()
                .where(c -> query.courses().contains(c.getId()))
                .map(c -> new CourseDto(c.getName()));

        return new SuccessResult<>(courses);
    }
}