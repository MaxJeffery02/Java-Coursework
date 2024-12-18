package application.results;

import domain.enums.HttpStatusCode;

public abstract class Result<T> {

    private final T data;
    private final boolean success;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public T getData() {
        if (isFailure()) throw new ClassCastException("Cannot get data when success is false");
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public String getMessageFromErrorResult() {
        if (this instanceof ErrorResult<T> errorResult) {
            errorResult.getMessage();
        }

        return "";
    }

    public HttpStatusCode getStatusCodeFromErrorResult(){
        if (this instanceof ErrorResult<T> errorResult) {
            errorResult.getStatusCode();
        }

        return HttpStatusCode.BAD_REQUEST;
    }
}