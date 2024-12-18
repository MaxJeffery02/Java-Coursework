package domain.valueobjects;

/**
 * Value object representing a password.
 * This object encapsulates the hashed password and its associated salt.
 * The hash and salt are used for securely storing and verifying the user's password.
 */
public class Password {

    // The hashed password string.
    private final String hash;

    // The salt used for hashing the password.
    private final String salt;

    /**
     * Constructor to initialize a Password object with the provided hash and salt.
     *
     * @param hash The hashed password string.
     * @param salt The salt used for the password hashing.
     */
    public Password(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    /**
     * Retrieves the hash of the password.
     *
     * @return The hashed password string.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Retrieves the salt used in the password hash.
     *
     * @return The salt string.
     */
    public String getSalt() {
        return salt;
    }
}