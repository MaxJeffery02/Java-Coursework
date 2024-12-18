package domain.helpers;

import com.google.gson.*;
import domain.entities.*;
import java.lang.reflect.Type;

/**
 * Custom deserializer for deserializing User objects from JSON using Gson.
 * This deserializer handles different types of users, including Student, Tutor, and Admin.
 * It also handles different subtypes of Student, such as Apprentice, ForeignExchange, and FullTimeStudent.
 */
public class UserDeserializer implements JsonDeserializer<User> {

    /**
     * Deserializes a JSON element into a User object.
     * Depending on the "type" field in the JSON, the deserializer returns different subclasses of User.
     *
     * @param json The JSON element to deserialize.
     * @param typeOfT The type of the object to deserialize into.
     * @param context The context of the deserialization process.
     * @return A User object, which can be of various types (Student, Tutor, Admin, etc.).
     * @throws JsonParseException If there is an issue with the JSON structure or an unknown user type.
     */
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JsonParseException("Missing 'type' field in JSON.");
        }
        String userType = typeElement.getAsString();

        return switch (userType.toUpperCase()) {
            case "STUDENT" -> deserializeStudent(jsonObject, context); // Deserialize student-specific fields
            case "TUTOR" -> context.deserialize(jsonObject, Tutor.class); // Deserialize Tutor object
            case "ADMIN" -> context.deserialize(jsonObject, Admin.class); // Deserialize Admin object
            default -> throw new JsonParseException("Unknown user type: " + userType); // Unknown user type
        };
    }

    /**
     * Helper method to deserialize Student objects from JSON.
     * Based on the "studentType" field in the JSON, this method returns the appropriate Student subclass.
     *
     * @param jsonObject The JSON object containing the student data.
     * @param context The context of the deserialization process.
     * @return A specific subclass of Student (Apprentice, ForeignExchange, FullTimeStudent).
     * @throws JsonParseException If there is an issue with the JSON structure or an unknown student type.
     */
    private Student deserializeStudent(JsonObject jsonObject, JsonDeserializationContext context) {
        JsonElement studentTypeElement = jsonObject.get("studentType");
        if (studentTypeElement == null) {
            throw new JsonParseException("Missing 'studentType' field for Student in JSON.");
        }
        String studentType = studentTypeElement.getAsString();

        return switch (studentType.toUpperCase()) {
            case "APPRENTICE" -> context.deserialize(jsonObject, Apprentice.class); // Deserialize Apprentice student
            case "FOREIGN_EXCHANGE" -> context.deserialize(jsonObject, ForeignExchange.class); // Deserialize ForeignExchange student
            case "FULL_TIME" -> context.deserialize(jsonObject, FullTimeStudent.class); // Deserialize FullTimeStudent student
            default -> throw new JsonParseException("Unknown student type: " + studentType); // Unknown student type
        };
    }
}
