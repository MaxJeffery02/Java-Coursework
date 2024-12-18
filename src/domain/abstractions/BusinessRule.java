package domain.abstractions;

/**
 * The BusinessRule interface represents a business rule that can be checked for validity or compliance.
 *
 * A business rule is a condition or restriction that must be satisfied to ensure that an operation
 * or process is allowed to proceed. This interface provides a contract for implementing specific
 * business rules, which can then be checked to determine if the rule has been broken.
 *
 * @return true if the rule is broken (invalid), otherwise false if the rule is valid.
 */
public interface BusinessRule {
    /**
     * Checks if the business rule has been broken or violated.
     *
     * @return true if the rule is broken, false otherwise.
     */
    boolean isBroken();
}
