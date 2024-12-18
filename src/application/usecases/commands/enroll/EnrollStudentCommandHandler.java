package application.usecases.commands.enroll;

import java.util.UUID;

import application.abstractions.Validator;
import domain.entities.Student;
import application.results.Result;
import domain.valueobjects.Password;
import application.results.ErrorResult;
import application.results.SuccessResult;
import infrastructure.database.DbContext;
import application.abstractions.CommandHandler;
import infrastructure.services.PasswordService;

public final class EnrollStudentCommandHandler implements CommandHandler<EnrollStudentCommand, UUID> {

    private final DbContext DB_CONTEXT;
    private final PasswordService PASSWORD_SERVICE;
    private final Validator<EnrollStudentCommand> VALIDATOR;

    public EnrollStudentCommandHandler() {
        this.DB_CONTEXT = new DbContext();
        this.PASSWORD_SERVICE = new PasswordService();
        this.VALIDATOR = new EnrollStudentCommandValidation();
    }

    @Override
    public Result<UUID> handle(EnrollStudentCommand command) {

        // Validate the command
        Result<?> validationResult = VALIDATOR.validate(command);

        // Early return if validation fails
        if(validationResult.isFailure()){
            ErrorResult<?> errorResult = (ErrorResult<?>) validationResult;
            return new ErrorResult<>(errorResult.getMessage(), errorResult.getStatusCode());
        }

        // Change this to generate random password
        Password password = PASSWORD_SERVICE.generate(command.password());

        // Try and create student
        Result<Student> createStudentResult = Student.create(
                command.firstName(),
                command.lastName(),
                password,
                command.dateOfBirth(),
                command.courseId(),
                command.type(),
                command.employer(),
                command.country()
        );

        // Early return if any of the business rules failed
        if(createStudentResult.isFailure()){
            ErrorResult<Student> errorResult = (ErrorResult<Student>) createStudentResult;
            return new ErrorResult<>(errorResult.getMessage(), errorResult.getStatusCode());
        }

        // Get student from the result
        Student student = createStudentResult.getData();

        // Save the user to the users json file
        DB_CONTEXT.getUsers().add(student);

        // Return success result
        return new SuccessResult<>(student.getId());
    }
}