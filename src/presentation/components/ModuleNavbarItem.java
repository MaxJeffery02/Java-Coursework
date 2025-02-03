package presentation.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import presentation.Main;

public class ModuleNavbarItem implements NavbarItem {
    private Menu modulesMenu;

    public ModuleNavbarItem() {
        modulesMenu = new Menu("Modules");
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        MenuItem addCoursesMenuItem = new MenuItem("Add");
        addCoursesMenuItem.setOnAction(_ -> handleAddCourse());
        modulesMenu.getItems().addAll(addCoursesMenuItem);
    }

    private void handleAddCourse() {
        Main.switchView("modules/add");
    }

    @Override
    public Menu getMenu() {
        return modulesMenu;
    }
}
