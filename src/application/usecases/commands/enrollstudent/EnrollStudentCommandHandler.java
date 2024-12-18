package application.usecases.commands.enrollstudent;

import java.util.UUID;

import application.abstractions.Validator;
import domain.entities.Course;
import domain.entities.Student;
import application.results.Result;
import domain.enums.HttpStatusCode;
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
            return new ErrorResult<>(
                    validationResult.getMessageFromErrorResult(),
                    validationResult.getStatusCodeFromErrorResult());
        }

        // Generate random password and hash it
        String plainTextPassword = PASSWORD_SERVICE.generateRandom(12);
        Password password = PASSWORD_SERVICE.generate(plainTextPassword);

        // Get course by id
        Course course = DB_CONTEXT.getCourses().firstOrDefault(c -> c.getId().equals(command.courseId()));

        // Early return if the course does not exist
        if(course == null){
            return new ErrorResult<>("Course does not exist", HttpStatusCode.NOT_FOUND);
        }

        // Try and create student
        Result<Student> createStudentResult = Student.create(
                command.firstName(),
                command.lastName(),
                password,
                command.dateOfBirth(),
                course,
                command.type(),
                command.employer(),
                command.country()
        );

        // Early return if any of the business rules failed
        if(createStudentResult.isFailure()){
            return new ErrorResult<>(
                    createStudentResult.getMessageFromErrorResult(),
                    createStudentResult.getStatusCodeFromErrorResult());
        }

        // Get student from the result
        Student student = createStudentResult.getData();

        // Save the user to the users json file
        DB_CONTEXT.getUsers().add(student);

        // Mock send a welcome email to the student
        student.sendWelcomeEmail(plainTextPassword);

        // Return success result
        return new SuccessResult<>(student.getId());
    }
}