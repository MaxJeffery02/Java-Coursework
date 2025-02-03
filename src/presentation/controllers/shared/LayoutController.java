package presentation.controllers.shared;

import application.dtos.Claim;
import application.dtos.ClaimTypes;
import infrastructure.services.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import presentation.Main;
import presentation.components.*;

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

        Claim userTypeClaim = sessionManager.getClaim(ClaimTypes.USER_TYPE);

        if (sessionManager.isLoggedIn() && userTypeClaim != null) {
            String userType = userTypeClaim.value().toLowerCase();

            if (userType.equals("admin")) {
                menuBar.getMenus().addAll(
                        new StudentNavbarItem().getMenu(),
                        new TutorNavbarItem().getMenu(),
                        new CourseNavbarItem().getMenu(),
                        new ModuleNavbarItem().getMenu()
                );
            }

            if(userType.equals("tutor")){
            }

            if(userType.equals("student")){
            }
        }

        // Add Menus to the MenuBar
        menuBar.getMenus().add(new ExitNavbarItem().getMenu());
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
