package cls;

public class AuthenticationState {
    private static boolean authenticated = false;
    private static boolean admin = false;

    // Check if the user is authenticated
    public static boolean isAuthenticated() {
        return authenticated;
    }

    // Authenticate the user and set the admin status
    public static void authenticate(boolean isAdmin) {
        authenticated = true;
        admin = isAdmin;
    }

    // Logout the user
    public static void logout() {
        authenticated = false;
        admin = false;
    }

    // Check if the authenticated user is an admin
    public static boolean isAdmin() {
        return admin;
    }
}
