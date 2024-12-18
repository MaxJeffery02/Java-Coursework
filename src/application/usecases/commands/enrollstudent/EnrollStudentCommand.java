package application.usecases.commands.enrollstudent;

import java.util.UUID;
import java.time.LocalDate;
import domain.enums.StudentType;
import application.abstractions.Command;

public record EnrollStudentCommand(
        String firstName,
        String lastName,
        String password,
        LocalDate dateOfBirth,
        UUID courseId,
        String employer,
        String country,
        StudentType type) implements Command<UUID> { }