package application.usecases.commands.createcourse;

import application.abstractions.Validator;
import domain.enums.HttpStatusCode;

public class CreateCourseCommandValidation extends Validator<CreateCourseCommand> {
    public CreateCourseCommandValidation() {
        ruleFor(CreateCourseCommand::name)
                .notEmpty()
                .withMessage("Course name is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
    }
}
