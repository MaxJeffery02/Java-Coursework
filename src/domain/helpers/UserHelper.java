package domain.helpers;

import infrastructure.database.DbContext;

public class UserHelper {

    public static String generateUsername(String firstName, String lastName) {
        // Construct base username
        char firstInitial = firstName.trim().toUpperCase().charAt(0);

        // Get up to the first 6 characters of the last name
        String lastNamePart = lastName.trim().toUpperCase();

        if (lastNamePart.length() > 6) {
            lastNamePart = lastNamePart.substring(0, 6);
        }

        String baseUsername = firstInitial + lastNamePart;

        // Initialize DbContext
        DbContext dbContext = new DbContext();

        // Count the existing users whose usernames start with the baseUsername
        int usersWithSameName = dbContext.getUsers()
                .count(u -> u.getUsername().startsWith(baseUsername));

        // Construct the final username
        String finalUsername = baseUsername;
        if (usersWithSameName > 0) {
            finalUsername = baseUsername + (usersWithSameName + 1);
        }

        return finalUsername;
    }
}