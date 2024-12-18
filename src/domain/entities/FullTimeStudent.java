package domain.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import domain.enums.StudentType;
import domain.valueobjects.Password;

public class FullTimeStudent extends Student {
    protected FullTimeStudent(String username, String firstName, String lastName, Password password, LocalDate dateOfBirth, StudentType studentType, UUID courseId) {
        super(username, firstName, lastName, password, dateOfBirth, studentType, courseId);
    }
}