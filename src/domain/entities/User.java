package domain.entities;

import java.time.LocalDate;

import domain.abstractions.Entity;
import domain.enums.UserType;
import domain.valueobjects.Password;

public class User extends Entity {
    /**
     * General properties
     */
    private String username;
    private String firstName;
    private String lastName;
    private UserType type;
    private LocalDate dateOfBirth;

    /**
     * Value objects
     */
    private Password password;

    protected User(String username, Password password, String firstName, String lastName, LocalDate dateOfBirth, UserType type) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.type = type;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Password getPassword() {
        return this.password;
    }

    public UserType getType() {
        return type;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getView() {
        return switch (type){
            case STUDENT -> "student/dashboard";
            case ADMIN -> "admin/dashboard";
            case TUTOR -> "tutor/dashboard";
        };
    }

    public String getEmail() {
        return getUsername() + "@fake.uni.co.uk";
    }
}