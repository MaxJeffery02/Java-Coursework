package application.usecases.commands.createtutor;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import application.abstractions.Command;

public record CreateTutorCommand(
        String firstName,
        String lastName,
        String password,
        LocalDate dateOfBirth,
        UUID courseId,
        List<UUID> courses) implements Command<UUID> { }