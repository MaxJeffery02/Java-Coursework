package domain.abstractions;

import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import com.google.gson.annotations.Expose;
import domain.enums.HttpStatusCode;

import java.util.UUID;
import java.util.function.Function;

/**
 * The Entity class serves as a base class for entities in the domain layer.
 * Every entity has a unique identifier (UUID) and provides utility methods
 * for checking business rules and mapping to other objects.
 *
 * This class is intended to be extended by domain entities that require
 * a unique ID and need to validate business rules.
 */
public abstract class Entity {

    /**
     * The unique identifier of the entity. Marked for serialization and deserialization
     * to facilitate JSON conversion using libraries like Gson.
     */
    @Expose(serialize = true, deserialize = true)
    protected final UUID id;

    /**
     * Constructor to initialize the entity with a unique UUID.
     */
    public Entity() {
        id = UUID.randomUUID();
    }

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return the UUID representing the entity's unique identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Checks if a business rule is violated. Returns a result indicating
     * whether the rule has been broken or not.
     *
     * @param rule The business rule to be validated.
     * @return a Result object, either an ErrorResult if the rule is broken,
     *         or a SuccessResult if the rule is satisfied.
     */
    protected static Result<?> checkBusinessRule(BusinessRule rule){
        return rule.isBroken() ?
                new ErrorResult<>("", HttpStatusCode.BAD_REQUEST) :
                new SuccessResult<>();
    }
}