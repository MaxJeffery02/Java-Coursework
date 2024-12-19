package presentation.controllers.shared;

import application.dtos.Claim;
import application.dtos.ClaimTypes;
import infrastructure.services.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

public class LayoutController {

    @FXML
    private StackPane contentPane;

    @FXML
    private MenuBar menuBar;

    private SessionManager sessionManager;

    /**
     * Initialize method to set up the menu based on user type.
     * This method is called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        sessionManager = SessionManager.getInstance();
        populateMenu();
    }

    public void populateMenu() {
        menuBar.getMenus().clear();

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> System.exit(0));
        fileMenu.getItems().add(exitItem);

        Menu viewMenu = new Menu("View");

        Claim userTypeClaim = sessionManager.getClaim(ClaimTypes.USER_TYPE);

        if(sessionManager.isLoggedIn() && userTypeClaim != null){
            String userType = userTypeClaim.value().toLowerCase();

            if (userType.equals("admin")) {
                MenuItem adminDashboard = new MenuItem("Admin Dashboard");
                viewMenu.getItems().add(adminDashboard);
            }

            if (userType.equals("user") || userType.equals("admin")) {
                MenuItem userDashboard = new MenuItem("User Dashboard");
                viewMenu.getItems().add(userDashboard);
            }
        }

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, viewMenu);
    }

    /**
     * Sets the content in the layout's center pane.
     * @param content The node to be displayed in the content area.
     */
    public void setContent(javafx.scene.Node content) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(content);
    }
}
