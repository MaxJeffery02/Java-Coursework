package application.abstractions;

import application.results.Result;

/**
 * The CommandHandler interface defines the contract for handling commands in the application.
 *
 * A command represents an action or operation that triggers a change in the system,
 * often involving side effects like updating data or performing a business operation.
 * This interface provides the method for handling the command and returning the result of that operation.
 *
 * @param <TCommand> The type of command that this handler can process.
 * @param <TResponse> The type of response that the command handler returns after executing the command.
 */
public interface CommandHandler<TCommand extends Command<TResponse>, TResponse> {

    /**
     * Handles the given command and returns the result of executing that command.
     *
     * @param command The command to be handled.
     * @return A result containing the response after executing the command.
     */
    Result<TResponse> handle(TCommand command);
}
