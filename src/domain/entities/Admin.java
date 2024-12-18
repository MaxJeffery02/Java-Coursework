package domain.entities;

import domain.enums.UserType;
import domain.valueobjects.Password;

import java.time.LocalDate;

public class Admin extends User {
    protected Admin(String username, Password password, String firstName, String lastName, LocalDate dateOfBirth, UserType type) {
        super(username, password, firstName, lastName, dateOfBirth, type);
    }
}