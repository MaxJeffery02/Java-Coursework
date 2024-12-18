package infrastructure.services;

import java.util.List;
import application.dtos.Claim;

public class SessionManager {
    private static SessionManager instance;
    private List<Claim> claims;

    // Private constructor to prevent instantiation
    private SessionManager() { }

    // Singleton instance accessor
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Store claims
    public synchronized void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    // Retrieve claims
    public synchronized List<Claim> getClaims() {
        return claims;
    }

    // Retrieve a specific claim by type
    public synchronized Claim getClaim(String claimType) {
        if (claims == null) return null;
        return claims.stream().filter(c -> c.type().equals(claimType)).findFirst().orElse(null);
    }

    // Clear the session
    public synchronized void clearSession() {
        this.claims = null;
    }

    // Check if a user is logged in
    public synchronized boolean isLoggedIn() {
        return claims != null && !claims.isEmpty();
    }
}