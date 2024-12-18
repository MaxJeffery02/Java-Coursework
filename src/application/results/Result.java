package application.results;

import domain.enums.HttpStatusCode;

public abstract class Result<T> {

    /** The data associated with the result. */
    private final T data;

    /** Indicates whether the result is a success or failure. */
    private final boolean success;

    /**
     * Constructor to initialize the result with its success status and associated data.
     * @param success A boolean indicating whether the result is a success.
     * @param data The data associated with the result.
     */
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * Returns the data if the result is a success, otherwise throws an exception.
     * @return The data associated with the result.
     * @throws ClassCastException If the result is a failure.
     */
    public T getData() {
        // If the result is a failure, return an error
        if (isFailure()) throw new ClassCastException("Cannot get data when success is false");
        return data;
    }

    /**
     * Checks if the result indicates success.
     * @return True if the result is a success, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Checks if the result indicates failure.
     * @return True if the result is a failure, false otherwise.
     */
    public boolean isFailure() {
        return !success;
    }

    /**
     * Retrieves the error message if the result is an error, otherwise returns an empty string.
     * @return The error message if the result is an error, or an empty string if it's a success.
     */
    public String getMessageFromErrorResult() {
        // If the result is an instance of ErrorResult, extract the error message
        if (this instanceof ErrorResult<T> errorResult) {
            return errorResult.getMessage();
        }

        return "";
    }

    /**
     * Retrieves the HTTP status code associated with the error result,
     * or returns a default BAD_REQUEST status code if the result is not an error.
     * @return The HTTP status code if the result is an error, or BAD_REQUEST otherwise.
     */
    public HttpStatusCode getStatusCodeFromErrorResult(){
        // If the result is an instance of ErrorResult, extract the status code
        if (this instanceof ErrorResult<T> errorResult) {
            return errorResult.getStatusCode();
        }

        // Return a default BAD_REQUEST status code if not an error
        return HttpStatusCode.BAD_REQUEST;
    }
}
