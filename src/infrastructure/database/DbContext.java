package infrastructure.database;

import domain.entities.Course;
import domain.entities.User;

public class DbContext {

    public DbSet<User> getUsers() {
        return new DbSet<>(User.class);
    }

    public DbSet<Course> getCourses() {
        return new DbSet<>(Course.class);
    }
}