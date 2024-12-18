package application.abstractions;

import java.util.List;
import java.util.ArrayList;
import application.results.Result;
import domain.enums.HttpStatusCode;
import java.util.function.Function;
import java.util.function.Predicate;
import application.results.ErrorResult;
import application.results.SuccessResult;

public abstract class Validator<TRequest> {

    private final List<ValidationRule<?>> rules = new ArrayList<>();

    protected <TProperty> ValidationRule<TProperty> ruleFor(Function<TRequest, TProperty> propertyFunction){
        ValidationRule<TProperty> rule = new ValidationRule<>(propertyFunction);
        rules.add(rule);
        return rule;
    }

    public Result<?> validate(TRequest request){
        for(ValidationRule<?> rule : rules){
            if(!rule.isValid(request)){
                return new ErrorResult<>(rule.getErrorMessage(), rule.getHttpStatusCode());
            }
        }

        return new SuccessResult<>();
    }

    protected class ValidationRule<TProperty>{
        private final Function<TRequest, TProperty> propertyFunction;

        private Predicate<TProperty> condition;

        private String errorMessage;
        private HttpStatusCode httpStatusCode;

        protected ValidationRule(Function<TRequest, TProperty> propertyFunction){
            this.propertyFunction = propertyFunction;
        }

        public ValidationRule<TProperty> notEmpty(){
            this.condition = property -> switch (property) {
                case null -> false;
                case String s -> !s.trim().isEmpty();
                case Number number -> !number.equals(0);
                default -> true;
            };
            return this;
        }

        public ValidationRule<TProperty> greaterThanOrEqual(int value) {
            this.condition = property -> {
                if (property == null) return false;

                // Assuming TProperty can be cast to Number, perform the comparison
                if (property instanceof Number) {
                    return ((Number) property).doubleValue() >= value;
                }

                // If property is not a Number, return false as it's not comparable
                return false;
            };
            return this;
        }

        public ValidationRule<TProperty> must(Predicate<TProperty> condition){
            this.condition = condition;
            return this;
        }

        public ValidationRule<TProperty> withMessage(String errorMessage){
            this.errorMessage = errorMessage;
            return this;
        }

        public ValidationRule<TProperty> withStatusCode(HttpStatusCode httpStatusCode){
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        private boolean isValid(TRequest request){
            TProperty propertyValue = propertyFunction.apply(request);
            return condition == null || condition.test(propertyValue);
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public HttpStatusCode getHttpStatusCode() {
            return httpStatusCode;
        }
    }
}