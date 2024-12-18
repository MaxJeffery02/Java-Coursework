package domain.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import domain.enums.StudentType;
import domain.valueobjects.Password;

public class ForeignExchange extends Student {

    private String country;

    protected ForeignExchange(String username, String firstName, String lastName, Password password, LocalDate dateOfBirth, StudentType studentType, Course course, String country) {
        super(username, firstName, lastName, password, dateOfBirth, studentType, course);
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}