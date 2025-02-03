package infrastructure.services;

import java.util.List;
import application.dtos.Claim;

public class SessionManager {

    // Singleton instance of SessionManager
    private static SessionManager instance;

    // List of claims associated with the current session
    private List<Claim> claims;

    /**
     * Private constructor to prevent instantiation from outside
     */
    private SessionManager() { }

    /**
     * Singleton accessor method that ensures only one instance is used
     * @return The single instance of SessionManager
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Set claims for the current session
     * @param claims The list of claims to be set
     */
    public synchronized void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    /**
     * Get all claims from the current session
     * @return The list of claims for the current session
     */
    public synchronized List<Claim> getClaims() {
        return claims;
    }

    /**
     * Get a specific claim by its type from the current session
     * @param claimType The type of claim to retrieve
     * @return The first matching claim, or null if no match is found
     */
    public synchronized Claim getClaim(String claimType) {
        if (claims == null) return null;
        return claims.stream().filter(c -> c.type().equals(claimType)).findFirst().orElse(null);
    }

    public  synchronized List<Claim> getClaims(String claimType) {
        if (claims == null) return null;
        return claims.stream().filter(c -> c.type().equals(claimType)).toList();
    }

    /**
     * Clear the session by removing all claims
     */
    public synchronized void clearSession() {
        this.claims = null;
    }

    /**
     * Check if the user is logged in (i.e., if claims exist)
     * @return True if the user is logged in, otherwise false
     */
    public synchronized boolean isLoggedIn() {
        return claims != null && !claims.isEmpty();
    }
}
