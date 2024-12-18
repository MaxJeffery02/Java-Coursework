package domain.helpers;

import com.google.gson.*;
import java.time.LocalDate;
import java.lang.reflect.Type;

/**
 * Custom adapter class for serializing and deserializing LocalDate objects
 * using the Gson library. This adapter converts LocalDate objects to
 * their string representation (ISO-8601 format) during serialization, and
 * converts string representations back to LocalDate during deserialization.
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    /**
     * Serializes a LocalDate object to its string representation (ISO-8601 format).
     * This method is called when Gson serializes a LocalDate object to JSON.
     *
     * @param src The LocalDate object to serialize.
     * @param typeOfSrc The type of the source object.
     * @param context The context of the serialization process.
     * @return A JsonElement representing the serialized LocalDate as a string.
     */
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString()); // Converts LocalDate to string in ISO-8601 format
    }

    /**
     * Deserializes a JSON element into a LocalDate object.
     * This method is called when Gson deserializes a string representation of a LocalDate back to a LocalDate object.
     *
     * @param json The JSON element containing the string representation of the LocalDate.
     * @param typeOfT The type of the deserialized object.
     * @param context The context of the deserialization process.
     * @return The LocalDate object created from the string representation.
     * @throws JsonParseException If the string cannot be parsed into a valid LocalDate.
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString()); // Parses the string to a LocalDate
    }
}
