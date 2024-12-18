package application.results;

public class SuccessResult<T> extends Result<T> {

    public SuccessResult(){
        super(true, null);
    }

    public SuccessResult(T data) {
        super(true, data);
    }
}