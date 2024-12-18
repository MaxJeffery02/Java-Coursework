package application.exceptions;

public class ViewNotFoundException extends RuntimeException {
    public ViewNotFoundException(String viewName) {
        super("View " + viewName + ".fxml does not exist!");
    }
}