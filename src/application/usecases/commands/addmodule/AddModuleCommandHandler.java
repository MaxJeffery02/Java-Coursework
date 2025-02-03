package application.usecases.commands.addmodule;

import application.abstractions.CommandHandler;
import application.abstractions.Validator;
import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import domain.entities.Course;
import domain.entities.Module;
import domain.enums.HttpStatusCode;
import infrastructure.database.DbContext;

import java.util.UUID;

public class AddModuleCommandHandler implements CommandHandler<AddModuleCommand, UUID> {

    private final DbContext DB_CONTEXT;
    private final Validator<AddModuleCommand> VALIDATOR;

    public AddModuleCommandHandler() {
        DB_CONTEXT = new DbContext();
        VALIDATOR = new AddModuleCommandValidation();
    }

    @Override
    public Result<UUID> handle(AddModuleCommand command) {

        // Validate the command
        Result<?> validationResult = VALIDATOR.validate(command);

        // Early return if validation fails
        if(validationResult.isFailure()){
            return new ErrorResult<>(
                    validationResult.getMessageFromErrorResult(),
                    validationResult.getStatusCodeFromErrorResult());
        }

        // Get course by id
        Course course = DB_CONTEXT.getCourses().firstOrDefault(c -> c.getId().equals(command.courseId()));

        // Early return if the course is not found
        if (course == null) {
            return new ErrorResult<UUID>("Could not find course", HttpStatusCode.NOT_FOUND);
        }

        // Create new module
        Module module = new Module(command.moduleName());

        // Add module to course
        course.addModule(module);
        DB_CONTEXT.getCourses().update(course);

        // Return success result with modules id
        return new SuccessResult<>(module.getId());
    }
}
