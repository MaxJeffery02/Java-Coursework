package domain.helpers;

import com.google.gson.*;
import domain.entities.*;
import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JsonParseException("Missing 'type' field in JSON.");
        }
        String userType = typeElement.getAsString();

        return switch (userType.toUpperCase()) {
            case "STUDENT" -> deserializeStudent(jsonObject, context);
            case "TUTOR" -> context.deserialize(jsonObject, Tutor.class);
            case "ADMIN" -> context.deserialize(jsonObject, Admin.class);
            default -> throw new JsonParseException("Unknown user type: " + userType);
        };
    }

    private Student deserializeStudent(JsonObject jsonObject, JsonDeserializationContext context) {
        JsonElement studentTypeElement = jsonObject.get("studentType");
        if (studentTypeElement == null) {
            throw new JsonParseException("Missing 'studentType' field for Student in JSON.");
        }
        String studentType = studentTypeElement.getAsString();

        return switch (studentType.toUpperCase()) {
            case "APPRENTICE" -> context.deserialize(jsonObject, Apprentice.class);
            case "FOREIGN_EXCHANGE" -> context.deserialize(jsonObject, ForeignExchange.class);
            case "FULL_TIME" -> context.deserialize(jsonObject, FullTimeStudent.class);
            default -> throw new JsonParseException("Unknown student type: " + studentType);
        };
    }

}
