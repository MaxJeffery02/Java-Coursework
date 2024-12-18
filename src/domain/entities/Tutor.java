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

    private List<UUID> coursesTaught;

    private Tutor(String username, Password password, String firstName, String lastName, LocalDate dateOfBirth, List<UUID> coursesTaught) {
        super(username, password, firstName, lastName, dateOfBirth, UserType.TUTOR);
        this.coursesTaught = coursesTaught;
    }

    public List<UUID> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<UUID> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    public static Result<Tutor> create(Password password, String firstName, String lastName, LocalDate dateOfBirth, List<UUID> coursesTaught){

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