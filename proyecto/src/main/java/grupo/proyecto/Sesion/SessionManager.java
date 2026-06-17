package grupo.proyecto.Sesion;

public class SessionManager {

    private static String accessToken;

    public static void setToken(String access) {
        accessToken = access;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void clear() {
        accessToken = null;
    }
}