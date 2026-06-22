package grupo.proyecto.UI.View.Restaurante;

import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteUpdateDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Sesion.SessionManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestauranteClient {

    private static final String API_URL =
            "http://localhost:8080/api/v1/restaurantes";

    private static final String AUTH_URL =
            "http://localhost:8080/auth";

    private final ObjectMapper mapper = new ObjectMapper();

    // =========================
    // TOKEN
    // =========================
    private void agregarToken(HttpURLConnection conn) {
        String token = SessionManager.getAccessToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
    }

    // =========================
    // REGISTRO (AUTH)
    // =========================
    public RestauranteResponseDTO registrar(RestauranteRequestDTO dto) throws Exception {

        URL url = new URL(AUTH_URL + "/register/restaurante");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            mapper.writeValue(os, dto);
        }

        if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {

            InputStream err = conn.getErrorStream();

            String body = (err != null)
                    ? new String(err.readAllBytes())
                    : "Sin detalle";

            throw new RuntimeException(body);
        }

        return mapper.readValue(conn.getInputStream(),
                RestauranteResponseDTO.class);
    }

    // =========================
    // CRUD PROTEGIDO
    // =========================
    public RestauranteResponseDTO obtenerRestaurante(Long id) throws Exception {

        URL url = new URL(API_URL + "/" + id);

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        agregarToken(conn);

        return mapper.readValue(
                conn.getInputStream(),
                RestauranteResponseDTO.class
        );
    }

    public RestauranteResponseDTO modificarRestaurante(Long id, RestauranteUpdateDTO dto) throws Exception {

        URL url = new URL(API_URL + "/" + id);

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        agregarToken(conn);

        try (OutputStream os = conn.getOutputStream()) {
            mapper.writeValue(os, dto);
        }

        int code = conn.getResponseCode();

        InputStream stream =
                (code >= 400) ? conn.getErrorStream() : conn.getInputStream();

        String response = new String(stream.readAllBytes());

        if (code >= 400) {
            throw new RuntimeException(response);
        }

        if (response.isBlank()) {
            return null;
        }

        return mapper.readValue(response, RestauranteResponseDTO.class);
    }
}