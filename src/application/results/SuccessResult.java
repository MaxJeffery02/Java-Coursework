package application.results;

/**
 * Represents a result indicating success, extending the base Result class.
 * This class holds the data returned in the case of a successful result.
 */
public class SuccessResult<T> extends Result<T> {

    /**
     * Default constructor to create a success result with no data.
     * This constructor calls the superclass constructor with success = true and data = null.
     */
    public SuccessResult() {
        // Call the constructor of the superclass (Result) to set the success status
        super(true, null);
    }

    /**
     * Constructor to create a success result with data.
     * @param data The data associated with the successful result.
     */
    public SuccessResult(T data) {
        // Call the constructor of the superclass (Result) to set the success status and provide data
        super(true, data);
    }
}
