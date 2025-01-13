package presentation.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import presentation.Main;

public class CourseNavbarItem  implements NavbarItem {
    private Menu coursesMenu;

    public CourseNavbarItem() {
        coursesMenu = new Menu("Courses");
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        MenuItem addCoursesMenuItem = new MenuItem("Add");
        addCoursesMenuItem.setOnAction(_ -> handleAddCourse());
        coursesMenu.getItems().addAll(addCoursesMenuItem);
    }

    private void handleAddCourse() {
        Main.switchView("courses/add");
    }

    @Override
    public Menu getMenu() {
        return coursesMenu;
    }
}
