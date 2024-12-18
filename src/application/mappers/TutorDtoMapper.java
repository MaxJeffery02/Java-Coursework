package application.mappers;

import application.abstractions.Mapper;
import application.dtos.TutorDto;
import domain.entities.Tutor;
import domain.entities.User;

public class TutorDtoMapper implements Mapper<User, TutorDto> {

    @Override
    public TutorDto map(User user) {
        return null;
    }
}