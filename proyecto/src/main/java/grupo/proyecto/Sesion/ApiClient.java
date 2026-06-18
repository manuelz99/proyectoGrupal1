package grupo.proyecto.Sesion;

import java.net.URI;
import java.net.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    // 🔑 LOGIN (JWT)
    public static String login(String email, String password) throws Exception {

        String body = """
        {
            "email": "%s",
            "password": "%s"
        }
        """.formatted(email, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error login: " + response.body());
        }

        // 📦 parseo JSON (SOLO Access Token)
        var json = mapper.readTree(response.body());
        String access = json.get("accessToken").asText();

        // 💾 guardamos token en sesión
        SessionManager.setToken(access);

        return access;
    }

    // 🔐 REQUEST GENÉRICO CON TOKEN (GET)
    public static HttpResponse<String> get(String endpoint) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Authorization", "Bearer " + SessionManager.getAccessToken())
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // 🔐 REQUEST GENÉRICO CON TOKEN (POST)
    public static HttpResponse<String> post(String endpoint, String jsonBody) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // 🔍 BUSCAR INFO DEL USUARIO LOGUEADO
    public static Long fetchLoggedInId() throws Exception {
        HttpResponse<String> response = get("/auth/me");

        var json = mapper.readTree(response.body());
        Long id = json.get("id").asLong();

        SessionManager.setLoggedInId(id); // Lo guardamos en sesión
        return id;
    }
}