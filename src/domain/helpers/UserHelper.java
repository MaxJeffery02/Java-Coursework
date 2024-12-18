package domain.helpers;

import infrastructure.database.DbContext;

/**
 * Helper class to generate a unique username based on the user's first and last name.
 * It ensures that the generated username is unique by checking existing usernames in the database.
 */
public class UserHelper {

    /**
     * Generates a unique username based on the user's first and last name.
     * The username is formed by the first letter of the first name and up to six characters of the last name.
     * If the generated username already exists, a numeric suffix is added to make it unique.
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @return A unique username for the user.
     */
    public static String generateUsername(String firstName, String lastName) {
        // Construct base username
        char firstInitial = firstName.trim().toUpperCase().charAt(0);

        // Get up to the first 6 characters of the last name
        String lastNamePart = lastName.trim().toUpperCase();

        if (lastNamePart.length() > 6) {
            lastNamePart = lastNamePart.substring(0, 6); // Limit to first 6 characters
        }

        // Combine first initial and last name part to create the base username
        String baseUsername = firstInitial + lastNamePart;

        // Initialize DbContext to interact with the database
        DbContext dbContext = new DbContext();

        // Count how many users have usernames starting with the same base username
        int usersWithSameName = dbContext.getUsers()
                .count(u -> u.getUsername().startsWith(baseUsername));

        // Construct the final username by appending a number if necessary
        String finalUsername = baseUsername;
        if (usersWithSameName > 0) {
            finalUsername = baseUsername + (usersWithSameName + 1); // Add a numeric suffix
        }

        return finalUsername; // Return the unique username
    }
}
