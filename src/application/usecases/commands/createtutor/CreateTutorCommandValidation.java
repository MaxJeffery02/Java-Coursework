package application.usecases.commands.createtutor;

import domain.enums.HttpStatusCode;
import application.abstractions.Validator;

public class CreateTutorCommandValidation extends Validator<CreateTutorCommand> {

    public CreateTutorCommandValidation() {
        ruleFor(CreateTutorCommand::firstName)
                .notEmpty()
                .withMessage("First name is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(CreateTutorCommand::lastName)
                .notEmpty()
                .withMessage("Last name is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(CreateTutorCommand::courses)
                .notEmpty()
                .withMessage("Course(s) is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(CreateTutorCommand::dateOfBirth)
                .notEmpty()
                .withMessage("Date of birth is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
    }
}
