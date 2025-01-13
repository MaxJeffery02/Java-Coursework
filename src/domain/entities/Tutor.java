package domain.entities;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import domain.enums.HttpStatusCode;
import domain.enums.UserType;
import domain.helpers.UserHelper;
import domain.rules.UserMustBeEighteenOrOver;
import domain.valueobjects.Password;

public class Tutor extends User {

    private List<Course> coursesTaught;

    private Tutor(String username, Password password, String firstName, String lastName, LocalDate dateOfBirth, List<Course> coursesTaught) {
        super(username, password, firstName, lastName, dateOfBirth, UserType.TUTOR);
        this.coursesTaught = coursesTaught;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    /**
     * Mock method for sending a welcome email to a tutor
     */
    public void sendWelcomeEmail(String plainTextPassword) {
        String subject = "Welcome " + getFirstName() + "!";

        String body = "";
        body += "Your login details are listed below:";
        body += "Username: " + getUsername();
        body += "Password: " + plainTextPassword;

        String to = getEmail();
        String from = "enrollment@fake.uni.co.uk";

        // Send email...
    }

    public static Result<Tutor> create(Password password, String firstName, String lastName, LocalDate dateOfBirth, List<Course> coursesTaught){

        if (checkBusinessRule(new UserMustBeEighteenOrOver(dateOfBirth)).isFailure()) {
            return new ErrorResult<>("Tutor is not old enough", HttpStatusCode.BAD_REQUEST);
        }

        String username = UserHelper.generateUsername(firstName, lastName);

        Tutor tutor = new Tutor(
                username,
                password,
                firstName,
                lastName,
                dateOfBirth,
                coursesTaught
        );

        return new SuccessResult<>(tutor);
    }
}