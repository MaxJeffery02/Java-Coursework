package application.usecases.commands.createtutor;

import java.util.List;
import java.util.UUID;

import application.abstractions.Validator;
import application.results.ErrorResult;
import application.usecases.commands.enrollstudent.EnrollStudentCommand;
import domain.entities.Course;
import domain.entities.Tutor;
import application.results.Result;
import domain.valueobjects.Password;
import application.results.SuccessResult;
import infrastructure.database.DbContext;
import application.abstractions.CommandHandler;
import infrastructure.services.PasswordService;

public final class CreateTutorCommandHandler implements CommandHandler<CreateTutorCommand, UUID> {

    private final DbContext DB_CONTEXT;
    private final PasswordService PASSWORD_SERVICE;
    private final Validator<CreateTutorCommand> VALIDATOR;

    public CreateTutorCommandHandler() {
        DB_CONTEXT = new DbContext();
        PASSWORD_SERVICE = new PasswordService();
        VALIDATOR = new CreateTutorCommandValidation();
    }

    @Override
    public Result<UUID> handle(CreateTutorCommand command) {

        // Validate the command
        Result<?> validationResult = VALIDATOR.validate(command);

        // Early return if validation fails
        if(validationResult.isFailure()){
            return new ErrorResult<>(
                    validationResult.getMessageFromErrorResult(),
                    validationResult.getStatusCodeFromErrorResult());
        }

        // Generate random password and hash it
        String plainTextPassword = PASSWORD_SERVICE.generateRandom(12);
        Password password = PASSWORD_SERVICE.generate(plainTextPassword);

        // Get the courses
        List<Course> courses = DB_CONTEXT.getCourses()
                .where(c -> command.courses().contains(c.getId()))
                .toList();

        // Try and create tutor using factory method
        Result<Tutor> createTutorResult = Tutor.create(
                password,
                command.firstName(),
                command.lastName(),
                command.dateOfBirth(),
                courses
        );

        // Early return if any of the business rules failed
        if(createTutorResult.isFailure()){
            return new ErrorResult<>(
                    createTutorResult.getMessageFromErrorResult(),
                    createTutorResult.getStatusCodeFromErrorResult()
            );
        }

        // Get tutor from the result
        Tutor tutor = createTutorResult.getData();

        // Save tutor to the users json
        DB_CONTEXT.getUsers().add(tutor);

        // Send mock welcome email to tutor
        tutor.sendWelcomeEmail(plainTextPassword);

        // Return success result
        return new SuccessResult<>(tutor.getId());
    }
}