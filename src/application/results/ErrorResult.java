package application.results;

import domain.enums.HttpStatusCode;

public class ErrorResult<T> extends Result<T> {

    private final String message;
    private final HttpStatusCode statusCode;

    public ErrorResult(String message, HttpStatusCode statusCode) {
        super(false, null);
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}