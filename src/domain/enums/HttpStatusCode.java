package domain.enums;

public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404);

    private final int code;

    // Constructor to assign the value of code
    HttpStatusCode(int code) {
        this.code = code;
    }

    // Getter to access the code value
    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.name() + " (" + code + ")";
    }
}