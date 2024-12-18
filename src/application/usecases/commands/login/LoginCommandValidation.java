package application.usecases.commands.login;

import application.abstractions.Validator;
import domain.enums.HttpStatusCode;

public class LoginCommandValidation extends Validator<LoginCommand>
{
    public LoginCommandValidation() {
        ruleFor(LoginCommand::username)
                .notEmpty()
                .withMessage("Username is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(LoginCommand::password)
                .notEmpty()
                .withMessage("Password is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
    }
}