package presentation.helpers;

import javafx.scene.control.Alert;

public class AlertHelper {

    /**
     * Helper method to show a message in a popup.
     * @param title The title to display.
     * @param message The message to display.
     * @param type The type of alert to display.
     */
    public static void show(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}