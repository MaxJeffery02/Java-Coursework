package application.usecases.commands.addmodule;

import application.abstractions.Command;

import java.util.UUID;

public record AddModuleCommand(UUID courseId, String moduleName) implements Command<UUID> {
}
