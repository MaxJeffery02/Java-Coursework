package application.usecases.commands.createcourse;

import application.abstractions.CommandHandler;
import application.abstractions.Validator;
import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import domain.entities.Course;
import infrastructure.database.DbContext;
import java.util.UUID;

public class CreateCourseCommandHandler implements CommandHandler<CreateCourseCommand, UUID> {

    private final DbContext DB_CONTEXT;
    private final Validator<CreateCourseCommand> VALIDATOR;


    public CreateCourseCommandHandler() {
        DB_CONTEXT = new DbContext();
        VALIDATOR = new CreateCourseCommandValidation();
    }

    @Override
    public Result<UUID> handle(CreateCourseCommand command) {

        // Validate the command
        Result<?> validationResult = VALIDATOR.validate(command);

        // Early return if validation fails
        if(validationResult.isFailure()){
            return new ErrorResult<>(
                    validationResult.getMessageFromErrorResult(),
                    validationResult.getStatusCodeFromErrorResult());
        }

        // Map command to course entity
        Course course = new Course(command.name());

        // Save course to json
        DB_CONTEXT.getCourses().add(course);

        // Return success result with courses id
        return new SuccessResult<>(course.getId());
    }
}
