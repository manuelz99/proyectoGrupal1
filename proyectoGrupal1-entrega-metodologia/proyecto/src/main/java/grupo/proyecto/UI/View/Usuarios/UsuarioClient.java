package grupo.proyecto.UI.View.Usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Sesion.SessionManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsuarioClient {

    private static final String API_URL =
            "http://localhost:8080/api/v1/usuarios";

    private static final String AUTH_URL =
            "http://localhost:8080/auth";

    private final ObjectMapper mapper = new ObjectMapper();

    // =========================
    // REGISTRO (AUTH)
    // =========================
    public void registrar(CrearUsuarioRequestDTO dto) throws Exception {

        URL url = new URL(AUTH_URL + "/register/usuario");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            mapper.writeValue(os, dto);
        }

        int code = conn.getResponseCode();

        if (code != 200 && code != 201) {

            InputStream err = conn.getErrorStream();

            String body = (err != null)
                    ? new String(err.readAllBytes())
                    : "Sin detalle";

            throw new RuntimeException(body);
        }
    }

    // =========================
    // LOGIN (AUTH)
    // =========================
    public UsuarioResponseDTO login(String email, String password) throws Exception {

        URL url = new URL(AUTH_URL + "/login");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        class LoginRequest {
            public String email;
            public String password;

            LoginRequest(String e, String p) {
                this.email = e;
                this.password = p;
            }
        }

        try (OutputStream os = conn.getOutputStream()) {
            mapper.writeValue(os, new LoginRequest(email, password));
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Login incorrecto");
        }

        return mapper.readValue(
                conn.getInputStream(),
                UsuarioResponseDTO.class
        );
    }

    // =========================
    // CRUD (API PROTEGIDA)
    // =========================
    public UsuarioResponseDTO buscarPorEmail(String email) throws Exception {

        URL url = new URL(API_URL + "/buscar/email?email=" + email);

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        return mapper.readValue(
                conn.getInputStream(),
                UsuarioResponseDTO.class
        );
    }
    public void agregarFavorito(Long usuarioId, Long restauranteId) throws Exception {

        URL url = new URL(API_URL + "/" + usuarioId + "/favoritos/agregar/" + restauranteId);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String token = SessionManager.getAccessToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        conn.setRequestProperty("Content-Type", "application/json");

        int code = conn.getResponseCode();

        if (code >= 400) {
            InputStream err = conn.getErrorStream();
            String body = new String(err.readAllBytes());
            throw new RuntimeException(body);
        }
    }
    public void eliminarFavorito(Long usuarioId, Long restauranteId) throws Exception {

        URL url = new URL(API_URL + "/" + usuarioId + "/favoritos/eliminar/" + restauranteId);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();


        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-Type", "application/json");


        String token = SessionManager.getAccessToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        int code = conn.getResponseCode();

        InputStream stream = (code >= 400)
                ? conn.getErrorStream()
                : conn.getInputStream();

        String body = new String(stream.readAllBytes());

        if (code != 200) {
            throw new RuntimeException("Error eliminando favorito: " + body);
        }
    }
    public String obtenerFavoritos(Long usuarioId) throws Exception {

        URL url = new URL(API_URL + "/" + usuarioId + "/favoritos");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");


        String token = SessionManager.getAccessToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        int code = conn.getResponseCode();

        InputStream stream = (code >= 400)
                ? conn.getErrorStream()
                : conn.getInputStream();

        String body = new String(stream.readAllBytes());

        if (code != 200) {
            throw new RuntimeException("Error obteniendo favoritos: " + body);
        }

        return body;
    }
}