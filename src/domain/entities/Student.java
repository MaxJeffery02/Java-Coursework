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

    private UUID courseId;
    private StudentType studentType;

    protected Student(
            String username,
            String firstName,
            String lastName,
            Password password,
            LocalDate dateOfBirth,
            StudentType studentType,
            UUID courseId) {
        super(username, password, firstName, lastName, dateOfBirth, UserType.STUDENT);
        this.studentType = studentType;
        this.courseId = courseId;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public static Result<Student> create(
            String firstName,
            String lastName,
            Password password,
            LocalDate dateOfBirth,
            UUID courseId,
            StudentType type,
            String employer,
            String country) {

        if (checkBusinessRule(new UserMustBeEighteenOrOver(dateOfBirth)).isFailure()) {
            return new ErrorResult<>("Student is not old enough", HttpStatusCode.BAD_REQUEST);
        }

        String username = UserHelper.generateUsername(firstName, lastName);

        Student student = switch (type) {
            case FOREIGN_EXCHANGE -> new ForeignExchange(username, firstName, lastName, password, dateOfBirth, type, courseId, country);
            case APPRENTICE -> new Apprentice(username, firstName, lastName, password, dateOfBirth, type, courseId, employer);
            default -> new FullTimeStudent(username, firstName, lastName, password, dateOfBirth, type, courseId);
        };

        return new SuccessResult<>(student);
    }
}