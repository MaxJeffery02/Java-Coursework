package application.abstractions;

import java.util.List;
import java.util.ArrayList;
import application.results.Result;
import domain.enums.HttpStatusCode;
import java.util.function.Function;
import java.util.function.Predicate;
import application.results.ErrorResult;
import application.results.SuccessResult;

/**
 * Abstract Validator class that provides functionality to validate a given request object.
 * It allows defining validation rules and checking if the given request satisfies them.
 *
 * This class supports the definition of rules for different properties within the request object
 * and provides detailed validation feedback via the Result pattern.
 */
public abstract class Validator<TRequest> {

    // List to store all validation rules
    private final List<ValidationRule<?>> rules = new ArrayList<>();

    /**
     * Creates and adds a validation rule for a specific property of the request object.
     *
     * @param propertyFunction Function to access the property from the request.
     * @param <TProperty> The type of the property.
     * @return A ValidationRule for the specified property.
     */
    protected <TProperty> ValidationRule<TProperty> ruleFor(Function<TRequest, TProperty> propertyFunction){
        ValidationRule<TProperty> rule = new ValidationRule<>(propertyFunction);
        rules.add(rule);
        return rule;
    }

    /**
     * Validates the request object based on all the rules defined in this validator.
     *
     * @param request The object to be validated.
     * @return Result indicating whether validation was successful or not.
     */
    public Result<?> validate(TRequest request){
        // Loop through all rules to check if they are valid for the request
        for(ValidationRule<?> rule : rules){
            if(!rule.isValid(request)){
                return new ErrorResult<>(rule.getErrorMessage(), rule.getHttpStatusCode());
            }
        }
        return new SuccessResult<>();
    }

    /**
     * Inner class representing a single validation rule for a property of the request.
     *
     * This rule contains a condition that checks the validity of a property and provides
     * custom error messages and status codes for failed validation.
     */
    protected class ValidationRule<TProperty> {

        // Function to extract the property value from the request
        private final Function<TRequest, TProperty> propertyFunction;

        // The condition that must be met for the validation to pass
        private Predicate<TProperty> condition;

        // Error message to be returned in case of failure
        private String errorMessage;

        // HTTP status code to return if the validation fails
        private HttpStatusCode httpStatusCode;

        /**
         * Constructs a ValidationRule for a given property function.
         *
         * @param propertyFunction The function that extracts the property value from the request.
         */
        protected ValidationRule(Function<TRequest, TProperty> propertyFunction){
            this.propertyFunction = propertyFunction;
        }

        /**
         * Specifies that the property should not be empty (for String or Number types).
         *
         * @return The current ValidationRule instance.
         */
        public ValidationRule<TProperty> notEmpty(){
            this.condition = property -> switch (property) {
                case null -> false;
                case String s -> !s.trim().isEmpty();
                case Number number -> !number.equals(0);
                default -> true;
            };
            return this;
        }

        /**
         * Specifies that the property should be greater than or equal to a given value.
         * This is useful for numeric properties.
         *
         * @param value The value that the property must be greater than or equal to.
         * @return The current ValidationRule instance.
         */
        public ValidationRule<TProperty> greaterThanOrEqual(int value) {
            this.condition = property -> {
                if (property == null) return false;

                // Check if the property is a number and compare it to the provided value
                if (property instanceof Number) {
                    return ((Number) property).doubleValue() >= value;
                }

                // If property is not a Number, return false as it's not comparable
                return false;
            };
            return this;
        }

        /**
         * Allows for custom conditions to be applied for validation.
         *
         * @param condition A custom condition (predicate) to validate the property.
         * @return The current ValidationRule instance.
         */
        public ValidationRule<TProperty> must(Predicate<TProperty> condition){
            this.condition = condition;
            return this;
        }

        /**
         * Sets a custom error message to be used when validation fails.
         *
         * @param errorMessage The error message to be shown in case of failure.
         * @return The current ValidationRule instance.
         */
        public ValidationRule<TProperty> withMessage(String errorMessage){
            this.errorMessage = errorMessage;
            return this;
        }

        /**
         * Sets the HTTP status code that will be returned in case of validation failure.
         *
         * @param httpStatusCode The HTTP status code.
         * @return The current ValidationRule instance.
         */
        public ValidationRule<TProperty> withStatusCode(HttpStatusCode httpStatusCode){
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        /**
         * Validates the property of the request using the defined condition.
         *
         * @param request The request object containing the property to validate.
         * @return True if the property satisfies the condition, false otherwise.
         */
        private boolean isValid(TRequest request){
            TProperty propertyValue = propertyFunction.apply(request);
            return condition == null || condition.test(propertyValue);
        }

        // Getter for error message
        public String getErrorMessage() {
            return errorMessage;
        }

        // Getter for HTTP status code
        public HttpStatusCode getHttpStatusCode() {
            return httpStatusCode;
        }
    }
}
