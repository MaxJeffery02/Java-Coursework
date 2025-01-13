package presentation.components;

import presentation.Main;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class TutorNavbarItem implements NavbarItem {
    private Menu tutorsMenu;

    public TutorNavbarItem() {
        tutorsMenu = new Menu("Tutors");
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        MenuItem addTutorMenuItem = new MenuItem("Add");
        MenuItem viewTutorMenuItem = new MenuItem("View");

        addTutorMenuItem.setOnAction(_ -> handleAddTutor());
        viewTutorMenuItem.setOnAction(_ -> handleViewTutor());

        tutorsMenu.getItems().addAll(addTutorMenuItem, viewTutorMenuItem);
    }

    private void handleAddTutor() {
        Main.switchView("tutors/add");
    }

    private void handleViewTutor() {
        Main.switchView("tutors/list");
    }

    @Override
    public Menu getMenu() {
        return tutorsMenu;
    }
}