package presentation.components;

import infrastructure.services.SessionManager;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import presentation.Main;

public class ExitNavbarItem implements NavbarItem {

    private Menu exitMenu;

    public ExitNavbarItem() {
        exitMenu = new Menu("Exit");
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        MenuItem logoutMenuItem = new MenuItem("Logout");
        MenuItem closeApplicationMenuItem = new MenuItem("Close Application");

        logoutMenuItem.setOnAction(_ -> handleLogout());
        closeApplicationMenuItem.setOnAction(_ -> handleCloseApplication());

        exitMenu.getItems().add(closeApplicationMenuItem);

        if(SessionManager.getInstance().isLoggedIn()){
            exitMenu.getItems().add(logoutMenuItem);
        }

    }

    private void handleLogout() {
        SessionManager.getInstance().clearSession();
        Main.switchView("authentication/login");
    }

    private void handleCloseApplication() {
        System.exit(0);
    }

    @Override
    public Menu getMenu() {
        return exitMenu;
    }
}
