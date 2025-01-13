package application.usecases.commands.createcourse;

import application.abstractions.Command;

import java.util.UUID;

public record CreateCourseCommand(String name) implements Command<UUID> {
}
