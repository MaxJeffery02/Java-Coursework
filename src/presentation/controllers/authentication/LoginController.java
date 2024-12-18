package presentation.controllers.authentication;

import application.dtos.LoginDto;
import application.results.Result;
import application.usecases.commands.login.LoginCommand;
import application.usecases.commands.login.LoginCommandHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import presentation.Main;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import presentation.helpers.AlertHelper;

public final class LoginController {

    /**
     * The username field from the FXML, where the user enters their username.
     */
    @FXML
    private TextField usernameField;

    /**
     * The password field from the FXML, where the user enters their password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * The command handler responsible for processing the login request.
     */
    private final LoginCommandHandler HANDLER;

    /**
     * Constructor for initializing the LoginController.
     * Initializes the LoginCommandHandler to handle login operations.
     */
    public LoginController(){
        // Instantiate the LoginCommandHandler
        HANDLER = new LoginCommandHandler();
    }

    /**
     * Handles the login action triggered by the user.
     * It validates the username and password, and if successful, navigates to the home page.
     */
    @FXML
    private void handleLogin() {
        // Create a LoginCommand with the username and password from the UI
        LoginCommand command = new LoginCommand(usernameField.getText(), passwordField.getText());

        // Handle the command and obtain the result
        Result<LoginDto> result = HANDLER.handle(command);

        // If the login result is a failure, exit the method (no further action)
        if(result.isFailure()){
            AlertHelper.show("Error", result.getMessageFromErrorResult(), Alert.AlertType.ERROR);
            return;
        }

        // If the login result is successful, switch to the home page of the user
        if(result.isSuccess()){
            // Switch to the home page based on the result data
            AlertHelper.show("Success", "User logged in", Alert.AlertType.INFORMATION);
            Main.switchView(result.getData().homePage());
        }
    }
}
