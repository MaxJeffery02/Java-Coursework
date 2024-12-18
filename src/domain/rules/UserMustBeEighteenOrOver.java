package domain.rules;

import java.time.Period;
import java.time.LocalDate;
import domain.abstractions.BusinessRule;

/**
 * Business rule that checks whether a user is 18 years old or older.
 * This rule ensures that the user meets the age requirement for specific operations or processes.
 */
public class UserMustBeEighteenOrOver implements BusinessRule {

    private final LocalDate dateOfBirth;

    /**
     * Constructor for the rule. Takes the user's date of birth to calculate their age.
     *
     * @param dateOfBirth The date of birth of the user to be checked.
     */
    public UserMustBeEighteenOrOver(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Checks if the rule is broken, i.e., if the user is under 18 years old.
     *
     * @return true if the user is under 18 years old, otherwise false.
     */
    @Override
    public boolean isBroken() {
        // Calculate the user's age by comparing their date of birth to the current date
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        // If the user is under 18 years old, the rule is broken
        return age < 18;
    }
}
