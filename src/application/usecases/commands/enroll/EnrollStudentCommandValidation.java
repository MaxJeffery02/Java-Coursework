package application.usecases.commands.enroll;

import domain.enums.HttpStatusCode;
import application.abstractions.Validator;

public class EnrollStudentCommandValidation extends Validator<EnrollStudentCommand> {

    public EnrollStudentCommandValidation() {
        ruleFor(EnrollStudentCommand::firstName)
                .notEmpty()
                .withMessage("First name is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(EnrollStudentCommand::lastName)
                .notEmpty()
                .withMessage("Last name is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(EnrollStudentCommand::password)
                .notEmpty()
                .withMessage("Password is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(EnrollStudentCommand::type)
                .notEmpty()
                .withMessage("Student type is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(EnrollStudentCommand::dateOfBirth)
                .notEmpty()
                .withMessage("Date of birth is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(EnrollStudentCommand::courseId)
                .notEmpty()
                .withMessage("Course is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
    }
}