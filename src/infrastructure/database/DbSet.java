package infrastructure.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.entities.User;
import domain.helpers.LocalDateAdapter;
import domain.helpers.UserDeserializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * DbSet<T> class is a generic data structure that facilitates interaction
 * with a collection of entities stored in a JSON file. It provides methods
 * for CRUD operations and querying capabilities such as filtering and mapping.
 */
public class DbSet<T> {

    // Gson instance for serialization and deserialization
    private final Gson GSON;

    // List to store entities
    private final List<T> ITEMS;

    // JSON file name based on entity type
    private final String JSON_NAME;

    // Predicate for filtering data (used in querying)
    private Predicate<T> predicate;

    /**
     * Constructor initializes DbSet with the type of entity, sets up Gson,
     * and loads items from the corresponding JSON file.
     *
     * @param type The class type of the entities in the DbSet.
     */
    public DbSet(Class<T> type) {
        this.GSON = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        this.JSON_NAME = "database/" + type.getSimpleName().toLowerCase() + "s.json";

        // Load items from the JSON file into the ITEMS list
        this.ITEMS = loadFromJson(type);
    }

    /**
     * Loads the items from the JSON file into the list.
     *
     * @param type The class type of the items to load.
     * @return A list of loaded items, or an empty list if the file doesn't exist or is empty.
     */
    private List<T> loadFromJson(Class<T> type) {
        try (FileReader fileReader = new FileReader(this.JSON_NAME)) {
            // Use TypeToken to get the type for List<T>
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            List<T> loadedItems = GSON.fromJson(fileReader, listType);

            return loadedItems != null ? loadedItems : new ArrayList<>();
        } catch (IOException e) {
            // If the file does not exist, return an empty list
            return new ArrayList<>();
        }
    }

    /**
     * Saves the current list of items to the JSON file.
     */
    private void saveToJson() {
        try (FileWriter fileWriter = new FileWriter(this.JSON_NAME)) {
            // Serialize the list and save it to the JSON file
            GSON.toJson(this.ITEMS, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save to JSON file: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new entity to the collection and saves it to the JSON file.
     *
     * @param entity The entity to add.
     */
    public void add(T entity) {
        this.ITEMS.add(entity);
        saveToJson();
    }

    /**
     * Removes an entity from the collection and saves the updated list to the JSON file.
     *
     * @param entity The entity to remove.
     */
    public void remove(T entity) {
        this.ITEMS.remove(entity);
        saveToJson();
    }

    /**
     * Updates an entity in the collection. It first removes the existing entity, then adds the updated entity.
     *
     * @param entity The entity to update.
     */
    public void update(T entity) {
        remove(entity);
        add(entity);
    }

    /**
     * Retrieves the first entity in the collection, or null if the collection is empty.
     *
     * @return The first entity, or null.
     */
    public T firstOrDefault() {
        return this.ITEMS.stream().findFirst().orElse(null);
    }

    /**
     * Retrieves the first entity that matches the given predicate, or null if none match.
     *
     * @param predicate The condition to filter the entities.
     * @return The first matching entity, or null.
     */
    public T firstOrDefault(Predicate<T> predicate) {
        return this.ITEMS.stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Converts the collection to a list, optionally filtered by the predicate.
     *
     * @return A list of all entities, filtered if a predicate is set.
     */
    public List<T> toList() {
        return this.predicate == null ? new ArrayList<>(this.ITEMS) : this.ITEMS.stream().filter(predicate).toList();
    }

    /**
     * Returns the number of entities in the collection.
     *
     * @return The count of entities.
     */
    public int count() {
        return this.ITEMS.size();
    }

    /**
     * Returns the number of entities that match the given predicate.
     *
     * @param predicate The condition to filter the entities.
     * @return The count of matching entities.
     */
    public int count(Predicate<T> predicate) {
        return (int) this.ITEMS.stream().filter(predicate).count();
    }

    /**
     * Returns true if the collection has any entities, false otherwise.
     *
     * @return Whether the collection has any entities.
     */
    public boolean any(){
        return !this.ITEMS.isEmpty();
    }

    /**
     * Returns true if any entity matches the given predicate, false otherwise.
     *
     * @param predicate The condition to filter the entities.
     * @return Whether any entity matches the predicate.
     */
    public boolean any(Predicate<T> predicate) {
        return this.ITEMS.stream().anyMatch(predicate);
    }

    /**
     * Maps the entities to a new type using the given mapper function.
     *
     * @param mapper The function to map each entity to a new type.
     * @param <TResult> The type of the result.
     * @return A list of mapped results.
     */
    public <TResult> List<TResult> map(Function<T, TResult> mapper) {
        Stream<T> items = predicate == null ?
                this.ITEMS.stream() :
                this.ITEMS.stream().filter(predicate);

        return items.map(mapper).toList();
    }

    /**
     * Filters the collection using the given predicate.
     *
     * @param predicate The condition to filter the entities.
     * @return This DbSet with the applied filter.
     */
    public DbSet<T> where(Predicate<T> predicate) {
        this.predicate = predicate;
        return this;
    }
}
