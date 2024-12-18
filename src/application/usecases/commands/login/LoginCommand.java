package application.usecases.commands.login;

import application.abstractions.Command;
import application.dtos.LoginDto;

public record LoginCommand(String username, String password) implements Command<LoginDto> { }