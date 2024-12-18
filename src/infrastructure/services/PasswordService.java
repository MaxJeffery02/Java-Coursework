package infrastructure.services;

import java.util.Base64;
import java.security.SecureRandom;
import domain.valueobjects.Password;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordService {

    private static final int SALT_LENGTH = 16; // 16 bytes for salt
    private static final int HASH_LENGTH = 64; // 64 bytes for hash
    private static final int ITERATIONS = 10000;

    /**
     * Generates a Password value object containing the hash and salt.
     *
     * @param plainPassword The plain text password to hash.
     * @return A Password object containing the hash and salt.
     */
    public Password generate(String plainPassword) {
        try {
            // Generate salt
            byte[] salt = generateSalt();

            // Generate hash
            byte[] hash = hashPassword(plainPassword, salt);

            // Convert salt and hash to Base64 for storage
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return new Password(hashBase64, saltBase64);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while generating password", e);
        }
    }

    /**
     * Verifies if the given plain text password matches the stored hash and salt.
     *
     * @param plainPassword The plain text password to verify.
     * @param password The Password object containing the stored hash and salt.
     * @return True if the password is valid, false otherwise.
     */
    public boolean verify(String plainPassword, Password password) {
        try {
            // Decode salt and hash from Base64
            byte[] salt = Base64.getDecoder().decode(password.getSalt());
            byte[] hash = Base64.getDecoder().decode(password.getHash());

            // Hash the input password with the same salt
            byte[] inputHash = hashPassword(plainPassword, salt);

            // Compare the hashes using constant-time comparison to prevent timing attacks
            return constantTimeEquals(hash, inputHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while verifying password", e);
        }
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH * 8);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return keyFactory.generateSecret(spec).getEncoded();
    }

    /**
     * Compares two byte arrays in constant time to prevent timing attacks.
     * This method is used to compare password hashes securely.
     *
     * @param a First byte array (hash to compare).
     * @param b Second byte array (expected hash).
     * @return True if the arrays are equal, false otherwise.
     */
    private boolean constantTimeEquals(byte[] a, byte[] b) {
        // Check if lengths are equal first to prevent excessive processing
        if (a.length != b.length) {
            return false;
        }

        // Use a constant-time comparison to avoid timing attacks
        int diff = 0;
        for (int i = 0; i < a.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
