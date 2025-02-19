package domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import domain.enums.HttpStatusCode;
import domain.enums.UserType;
import domain.enums.StudentType;
import domain.helpers.UserHelper;
import domain.rules.UserMustBeEighteenOrOver;
import domain.valueobjects.Password;

public class Student extends User {

    private Course course;
    private StudentType studentType;

    protected Student(
            String username,
            String firstName,
            String lastName,
            Password password,
            LocalDate dateOfBirth,
            StudentType studentType,
            Course course) {
        super(username, password, firstName, lastName, dateOfBirth, UserType.STUDENT);
        this.studentType = studentType;
        this.course = course;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    /**
     * Mock method for sending a welcome email to a student
     */
    public void sendWelcomeEmail(String plainTextPassword) {
        String subject = "Welcome " + getFirstName() + "!";

        String body = "You have been enrolled on " + getCourse().getName();
        body += "Your login details are listed below:";
        body += "Username: " + getUsername();
        body += "Password: " + plainTextPassword;

        String to = getEmail();
        String from = "enrollment@fake.uni.co.uk";

        // Send email...
    }

    public static Result<Student> create(
            String firstName,
            String lastName,
            Password password,
            LocalDate dateOfBirth,
            Course course,
            StudentType type,
            String employer,
            String country) {

        if (checkBusinessRule(new UserMustBeEighteenOrOver(dateOfBirth)).isFailure()) {
            return new ErrorResult<>("Student is not old enough", HttpStatusCode.BAD_REQUEST);
        }

        String username = UserHelper.generateUsername(firstName, lastName);

        Student student = switch (type) {
            case FOREIGN_EXCHANGE -> new ForeignExchange(username, firstName, lastName, password, dateOfBirth, type, course, country);
            case APPRENTICE -> new Apprentice(username, firstName, lastName, password, dateOfBirth, type, course, employer);
            default -> new FullTimeStudent(username, firstName, lastName, password, dateOfBirth, type, course);
        };

        return new SuccessResult<>(student);
    }
}