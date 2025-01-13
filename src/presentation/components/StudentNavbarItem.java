package presentation.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import presentation.Main;

public class StudentNavbarItem implements NavbarItem {
    private Menu studentsMenu;

    public StudentNavbarItem() {
        studentsMenu = new Menu("Students");
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        MenuItem addStudentMenuItem = new MenuItem("Add");
        MenuItem viewStudentMenuItem = new MenuItem("View");

        addStudentMenuItem.setOnAction(_ -> handleAddStudent());
        viewStudentMenuItem.setOnAction(_ -> handleViewStudents());

        studentsMenu.getItems().addAll(addStudentMenuItem, viewStudentMenuItem);
    }

    private void handleAddStudent() {
        Main.switchView("students/add");
    }

    private void handleViewStudents() {
        Main.switchView("students/list");
    }

    @Override
    public Menu getMenu() {
        return studentsMenu;
    }
}
