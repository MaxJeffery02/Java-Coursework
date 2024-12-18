package application.usecases.commands.createtutor;

import java.util.UUID;

import application.results.ErrorResult;
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

    public CreateTutorCommandHandler() {
        DB_CONTEXT = new DbContext();
        PASSWORD_SERVICE = new PasswordService();
    }

    @Override
    public Result<UUID> handle(CreateTutorCommand command) {

        // Hash and salt the password
        Password password = PASSWORD_SERVICE.generate(command.password());

        // Try and create tutor
        Result<Tutor> createTutorResult = Tutor.create(
                password,
                command.firstName(),
                command.lastName(),
                command.dateOfBirth(),
                command.courses()
        );

        // Early return if any of the business rules failed
        if(createTutorResult.isFailure()){
            ErrorResult<Tutor> errorResult = (ErrorResult<Tutor>) createTutorResult;
            return new ErrorResult<>(errorResult.getMessage(), errorResult.getStatusCode());
        }

        // Get tutor from the result
        Tutor tutor = createTutorResult.getData();

        // Save tutor to the users json
        DB_CONTEXT.getUsers().add(tutor);

        // Return success result
        return new SuccessResult<>(tutor.getId());
    }
}