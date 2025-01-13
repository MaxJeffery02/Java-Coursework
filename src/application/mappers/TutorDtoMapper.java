package application.mappers;

import application.abstractions.Mapper;
import application.dtos.TutorDto;
import domain.entities.Course;
import domain.entities.Tutor;
import domain.entities.User;

import java.util.stream.Collectors;

public class TutorDtoMapper implements Mapper<User, TutorDto> {

    @Override
    public TutorDto map(User user) {

        if (user instanceof Tutor) {

            String coursesTaught = ((Tutor) user).getCoursesTaught()
                    .stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));

            return new TutorDto(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    coursesTaught
            );
        }

        return null;
    }
}