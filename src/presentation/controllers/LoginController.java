package presentation.controllers;

import application.dtos.LoginDto;
import application.results.Result;
import application.usecases.commands.login.LoginCommand;
import application.usecases.commands.login.LoginCommandHandler;
import javafx.fxml.FXML;
import presentation.Main;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final LoginCommandHandler HANDLER;

    public LoginController(){
        HANDLER = new LoginCommandHandler();
    }

    @FXML
    private void handleLogin() {
        LoginCommand command = new LoginCommand(usernameField.getText(), passwordField.getText());

        Result<LoginDto> result = HANDLER.handle(command);

        if(result.isFailure()){
            Main.makeText(result.getMessageFromErrorResult());
            return;
        }

        if(result.isSuccess()){
            Main.makeText("Logged in!");
            Main.switchView(result.getData().homePage());
        }
    }
}