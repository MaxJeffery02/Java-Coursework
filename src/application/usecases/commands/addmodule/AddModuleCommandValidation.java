package application.usecases.commands.addmodule;

import application.abstractions.Validator;
import application.usecases.commands.createcourse.CreateCourseCommand;
import domain.enums.HttpStatusCode;

public class AddModuleCommandValidation extends Validator<AddModuleCommand> {

    public AddModuleCommandValidation() {
        ruleFor(AddModuleCommand::moduleName)
                .notEmpty()
                .withMessage("Module name is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);

        ruleFor(AddModuleCommand::courseId)
                .notEmpty()
                .withMessage("Course is required")
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
    }
}
