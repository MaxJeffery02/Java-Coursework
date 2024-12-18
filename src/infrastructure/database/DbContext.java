package infrastructure.database;

import domain.entities.Course;
import domain.entities.Tutor;
import domain.entities.User;

/**
 * DbContext class is used to provide access to the DbSet of entities in the JSON.
 * It acts as a bridge between the JSON and the application, allowing CRUD operations on different entities.
 */
public class DbContext {

    /**
     * Gets the DbSet of User entities.
     *
     * @return A DbSet<User> that allows interaction with the User entities in the JSON.
     */
    public DbSet<User> getUsers() {
        // Return a DbSet that provides CRUD operations on User entities
        return new DbSet<>(User.class);
    }

    /**
     * Gets the DbSet of Course entities.
     *
     * @return A DbSet<Course> that allows interaction with the Course entities in the JSON.
     */
    public DbSet<Course> getCourses() {
        // Return a DbSet that provides CRUD operations on Course entities
        return new DbSet<>(Course.class);
    }
}