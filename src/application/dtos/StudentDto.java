package application.dtos;

import javafx.beans.property.SimpleStringProperty;

public class StudentDto {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty course;
    private final SimpleStringProperty type;
    private final SimpleStringProperty employer;
    private final SimpleStringProperty country;

    public StudentDto(String firstName, String lastName, String userName, String course, String type, String employer, String country) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.course = new SimpleStringProperty(course);
        this.type = new SimpleStringProperty(type);
        this.employer = new SimpleStringProperty(employer);
        this.country = new SimpleStringProperty(country);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public String getCourse() {
        return course.get();
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getEmployer() {
        return employer.get();
    }

    public SimpleStringProperty employerProperty() {
        return employer;
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }
}
