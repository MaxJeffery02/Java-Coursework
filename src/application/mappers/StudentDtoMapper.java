package application.mappers;

import domain.entities.User;
import domain.entities.Student;
import domain.entities.Apprentice;
import application.dtos.StudentDto;
import domain.entities.ForeignExchange;
import application.abstractions.Mapper;

public class StudentDtoMapper implements Mapper<User, StudentDto> {

    @Override
    public StudentDto map(User user) {
        if(user instanceof Student student){
            String employer = student instanceof Apprentice ? ((Apprentice) student).getEmployer() : "";
            String country = student instanceof ForeignExchange ? ((ForeignExchange) student).getCountry() : "";

            return new StudentDto(
                    student.getFirstName(),
                    student.getLastName(),
                    student.getUsername(),
                    "",
                    student.getStudentType().getName(),
                    employer,
                    country);
        }
        return null;
    }
}