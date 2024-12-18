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

public class DbSet<T> {

    private final Gson GSON;
    private final List<T> ITEMS;
    private final String JSON_NAME;

    private Predicate<T> predicate;

    public DbSet(Class<T> type) {
        this.GSON = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        this.JSON_NAME = "database/" + type.getSimpleName().toLowerCase() + "s.json";

        // Load items from the JSON file
        this.ITEMS = loadFromJson(type);
    }

    private List<T> loadFromJson(Class<T> type) {
        try (FileReader fileReader = new FileReader(this.JSON_NAME)) {
            // Create a TypeToken for the List of T
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            List<T> loadedItems = GSON.fromJson(fileReader, listType);

            return loadedItems != null ? loadedItems : new ArrayList<>();
        } catch (IOException e) {
            // If file does not exist return empty array
            return new ArrayList<>();
        }
    }

    private void saveToJson() {
        try (FileWriter fileWriter = new FileWriter(this.JSON_NAME)) {
            GSON.toJson(this.ITEMS, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save to JSON file: " + e.getMessage(), e);
        }
    }

    public DbSet<T> where(Predicate<T> predicate){
        this.predicate = predicate;
        return this;
    }

    public void add(T entity) {
        this.ITEMS.add(entity);
        saveToJson();
    }

    public void remove(T entity) {
        this.ITEMS.remove(entity);
        saveToJson();
    }

    public void update(T entity) {
        remove(entity);
        add(entity);
    }

    public T firstOrDefault() {
        return this.ITEMS.stream().findFirst().orElse(null);
    }

    public T firstOrDefault(Predicate<T> predicate) {
        return this.ITEMS.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<T> toList() {
        return this.predicate == null ? new ArrayList<>(this.ITEMS) : this.ITEMS.stream().filter(predicate).toList();
    }

    public int count() {
        return this.ITEMS.size();
    }

    public int count(Predicate<T> predicate) {
        return (int) this.ITEMS.stream().filter(predicate).count();
    }

    public boolean any(){
        return !this.ITEMS.isEmpty();
    }

    public boolean any(Predicate<T> predicate) {
        return this.ITEMS.stream().anyMatch(predicate);
    }

    public <TResult> List<TResult> map(Function<T, TResult> mapper) {
        Stream<T> items = predicate == null ?
                this.ITEMS.stream() :
                this.ITEMS.stream().filter(predicate);

        return items.map(mapper)
                .toList();
    }
}