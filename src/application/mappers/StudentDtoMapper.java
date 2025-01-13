package application.mappers;

import domain.entities.User;
import domain.entities.Student;
import domain.entities.Apprentice;
import application.dtos.StudentDto;
import domain.entities.ForeignExchange;
import application.abstractions.Mapper;

/**
 * Mapper implementation to convert a User (specifically a Student) into a StudentDto.
 * This is used for transforming data between entities and Data Transfer Objects (DTOs).
 *
 * This class checks the specific type of student (Apprentice, ForeignExchange) and
 * extracts additional details such as employer or country accordingly.
 */
public class StudentDtoMapper implements Mapper<User, StudentDto> {

    /**
     * Maps a User object to a StudentDto.
     * If the User is an instance of Student (or its subclasses like Apprentice or ForeignExchange),
     * it maps the properties to a StudentDto.
     *
     * @param user The User entity (usually a Student or subclass).
     * @return A StudentDto with details from the user.
     */
    @Override
    public StudentDto map(User user) {
        // Check if the user is a Student
        if(user instanceof Student student){

            // Extract additional details based on the type of student
            String employer = student instanceof Apprentice ? ((Apprentice) student).getEmployer() : "N/A";
            String country = student instanceof ForeignExchange ? ((ForeignExchange) student).getCountry() : "N/A";

            // Return a new StudentDto with the mapped data
            return new StudentDto(
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getCourse().getName(),
                    student.getStudentType().toString(),
                    employer,
                    country);
        }
        // Return null if the user is not a Student
        return null;
    }
}
