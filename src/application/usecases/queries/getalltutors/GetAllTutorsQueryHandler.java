package application.usecases.queries.getalltutors;

import application.abstractions.Mapper;
import application.abstractions.QueryHandler;
import application.dtos.TutorDto;
import application.mappers.TutorDtoMapper;
import application.results.Result;
import application.results.SuccessResult;
import domain.entities.User;
import infrastructure.database.DbContext;

import java.util.List;

public final class GetAllTutorsQueryHandler implements QueryHandler<GetAllTutorsQuery, List<TutorDto>> {

    private final DbContext DB_CONTEXT;
    private final Mapper<User, TutorDto> MAPPER;

    public GetAllTutorsQueryHandler() {
        this.DB_CONTEXT = new DbContext();
        this.MAPPER = new TutorDtoMapper();
    }

    @Override
    public Result<List<TutorDto>> handle(GetAllTutorsQuery query) {

        List<TutorDto> tutors = DB_CONTEXT.getUsers()
                .map(MAPPER::map);

        return new SuccessResult<>(tutors);
    }
}