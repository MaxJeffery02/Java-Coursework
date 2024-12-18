package domain.entities;

import java.util.UUID;
import java.time.LocalDate;
import domain.enums.StudentType;
import domain.valueobjects.Password;

public class Apprentice extends Student {

    private String employer;

    protected Apprentice(String username, String firstName, String lastName, Password password, LocalDate dateOfBirth, StudentType studentType, UUID courseId, String employer) {
        super(username, firstName, lastName, password, dateOfBirth, studentType, courseId);
        this.employer = employer;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }
}