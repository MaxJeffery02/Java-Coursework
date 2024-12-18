package domain.abstractions;

import application.results.ErrorResult;
import application.results.Result;
import application.results.SuccessResult;
import com.google.gson.annotations.Expose;
import domain.enums.HttpStatusCode;

import java.util.UUID;
import java.util.function.Function;

public abstract class Entity {

    @Expose(serialize = true, deserialize = true)
    protected final UUID id;

    public Entity() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    protected static Result<?> checkBusinessRule(BusinessRule rule){
        return rule.isBroken() ?
                new ErrorResult<>("", HttpStatusCode.BAD_REQUEST) :
                new SuccessResult<>();
    }

    public <TResult> TResult map(Function<Entity, TResult> mapper) {
        return mapper.apply(this);
    }
}