package application.abstractions;

/**
 * The Command interface represents an abstraction for commands in the application.
 *
 * A command is a request to perform an operation or action, typically involving side effects
 * like changing state, updating data, or executing a process. This interface serves as a marker
 * for all command types, ensuring they conform to the same structure and can be processed uniformly.
 *
 * @param <T> The type of response that is expected after executing the command.
 */
public interface Command<T> {
}
