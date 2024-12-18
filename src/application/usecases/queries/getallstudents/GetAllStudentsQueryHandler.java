package application.usecases.queries.getallstudents;

import java.util.List;

import application.abstractions.Mapper;
import application.mappers.StudentDtoMapper;
import application.results.Result;
import application.dtos.StudentDto;
import application.results.SuccessResult;
import domain.entities.User;
import domain.enums.UserType;
import infrastructure.database.DbContext;
import application.abstractions.QueryHandler;

public final class GetAllStudentsQueryHandler implements QueryHandler<GetAllStudentsQuery, List<StudentDto>> {

    private final DbContext DBCONTEXT;
    private final Mapper<User, StudentDto> STUDENTMAPPER;

    public GetAllStudentsQueryHandler() {
        this.DBCONTEXT = new DbContext();
        this.STUDENTMAPPER = new StudentDtoMapper();
    }

    @Override
    public Result<List<StudentDto>> handle(GetAllStudentsQuery query) {

        // Get users from json and map them to student dto
        List<StudentDto> students = DBCONTEXT.getUsers()
                .where(u -> u.getType().equals(UserType.STUDENT))
                .map(STUDENTMAPPER::map);

        return new SuccessResult<>(students);
    }
}