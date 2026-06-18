package grupo.proyecto.Sesion;

public class SessionManager {

    private static String accessToken;
    private static Long loggedInId; // Agregamos esta variable

    public static void setToken(String access) { accessToken = access; }
    public static String getAccessToken() { return accessToken; }

    // Agregamos Getters y Setters para el ID
    public static void setLoggedInId(Long id) { loggedInId = id; }
    public static Long getLoggedInId() { return loggedInId; }

    public static void clear() {
        accessToken = null;
        loggedInId = null;
    }
}