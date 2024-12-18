package application.usecases.commands.login;

import application.abstractions.Validator;
import application.dtos.Claim;
import application.dtos.ClaimTypes;
import application.results.SuccessResult;
import domain.entities.Student;
import domain.entities.Tutor;
import domain.entities.User;
import application.dtos.LoginDto;
import application.results.Result;
import domain.enums.HttpStatusCode;
import application.results.ErrorResult;
import infrastructure.database.DbContext;
import application.abstractions.CommandHandler;
import infrastructure.services.PasswordService;
import infrastructure.services.SessionManager;

import java.util.ArrayList;
import java.util.List;

public final class LoginCommandHandler implements CommandHandler<LoginCommand, LoginDto> {

    private final DbContext DB_CONTEXT;
    private final PasswordService PASSWORD_SERVICE;
    private final Validator<LoginCommand> VALIDATOR;

    public LoginCommandHandler() {
        DB_CONTEXT = new DbContext();
        PASSWORD_SERVICE = new PasswordService();
        VALIDATOR = new LoginCommandValidation();
    }

    @Override
    public Result<LoginDto> handle(LoginCommand command) {

        // Validate the command
        Result<?> validationResult = VALIDATOR.validate(command);

        // Early return if validation fails
        if (validationResult.isFailure()) {
            return new ErrorResult<>(
                    validationResult.getMessageFromErrorResult(),
                    validationResult.getStatusCodeFromErrorResult());
        }

        // Try and find user by username
        User user = DB_CONTEXT.getUsers().firstOrDefault(u -> u.getUsername().equals(command.username()));

        // Early return if user was not found
        if (user == null) return new ErrorResult<>("Username was incorrect", HttpStatusCode.BAD_REQUEST);

        // Check if the password matches the user's password
        boolean isPasswordValid = PASSWORD_SERVICE.verify(command.password(), user.getPassword());

        // Early return if password was incorrect
        if (!isPasswordValid) return new ErrorResult<>("Password was incorrect", HttpStatusCode.BAD_REQUEST);

        // Set claims in SessionManager
        setClaims(user);

        // Return success result
        return new SuccessResult<>(new LoginDto(user.getView()));
    }

    private static void setClaims(User user) {
        // Define claims for the user
        List<Claim> claims = new ArrayList<>();

        // Add normal users claims
        claims.add(new Claim(ClaimTypes.USER_NAME, user.getUsername()));
        claims.add(new Claim(ClaimTypes.USER_ID, user.getId().toString()));
        claims.add(new Claim(ClaimTypes.USER_TYPE, user.getType().toString()));

        // Add student claims if user is a student
        if(user instanceof Student student){
            claims.add(new Claim(ClaimTypes.COURSE_ID, student.getCourseId().toString()));
            claims.add(new Claim(ClaimTypes.STUDENT_TYPE, student.getStudentType().toString()));
        }

        // Add tutor claims if a user is a tutor
        if(user instanceof Tutor tutor){
            tutor.getCoursesTaught().forEach(c -> {
                claims.add(new Claim(ClaimTypes.COURSE_ID, c.toString()));
            });
        }

        // Set claims in the SessionManager
        SessionManager.getInstance().setClaims(claims);
    }
}
