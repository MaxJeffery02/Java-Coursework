package application.results;

import domain.enums.HttpStatusCode;

/**
 * Represents a result indicating an error, extending the base Result class.
 * This class holds an error message and an HTTP status code.
 */
public class ErrorResult<T> extends Result<T> {

    /** The error message associated with the error result. */
    private final String message;

    /** The HTTP status code associated with the error result. */
    private final HttpStatusCode statusCode;

    /**
     * Constructor to initialize an error result with a message and HTTP status code.
     * @param message The error message describing the issue.
     * @param statusCode The HTTP status code representing the error's category.
     */
    public ErrorResult(String message, HttpStatusCode statusCode) {
        // Call the constructor of the superclass (Result) to set the failure status
        super(false, null);
        this.message = message;
        this.statusCode = statusCode;
    }

    /**
     * Retrieves the error message associated with the error result.
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the HTTP status code associated with the error result.
     * @return The HTTP status code.
     */
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
