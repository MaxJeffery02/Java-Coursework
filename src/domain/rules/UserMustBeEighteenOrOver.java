package domain.rules;

import java.time.Period;
import java.time.LocalDate;
import domain.abstractions.BusinessRule;

public class UserMustBeEighteenOrOver implements BusinessRule {

    private final LocalDate dateOfBirth;

    public UserMustBeEighteenOrOver(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean isBroken() {
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        // If the student is under 18 years old, the rule is broken
        return age < 18;
    }
}
