package grupo.proyecto.UI.View.Restaurante;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.PlatoUpdateDTO;
import grupo.proyecto.Models.dto.request.PromocionRequestDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Sesion.SessionManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PlatoClient {

    private static final String API_URL =
            "http://localhost:8080/api/v1/restaurantes";

    // =========================
    // GET PLATOS POR RESTAURANTE
    // =========================
    public List<PlatoResponseDTO> obtenerPlatos(Long idRestaurante) throws Exception {

        URL url = new URL(API_URL + "/" + idRestaurante + "/platos");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        agregarToken(connection);

        int status = connection.getResponseCode();
        InputStream stream = (status >= 400)
                ? connection.getErrorStream()
                : connection.getInputStream();

        String response = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        if (status >= 400) {
            throw new RuntimeException("Error backend: " + response);
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<>() {});
    }

    // =========================
    // CREAR PLATO
    // =========================
    public PlatoResponseDTO crearPlato(Long idRestaurante, PlatoRequestDTO dto) throws Exception {

        URL url = new URL(API_URL + "/" + idRestaurante + "/platos");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        agregarToken(connection);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        return leerRespuesta(connection, mapper);
    }

    // =========================
    // ELIMINAR PLATO
    // =========================
    public void eliminarPlato(Long idRestaurante, Long platoId) throws Exception {

        URL url = new URL(API_URL + "/" + idRestaurante + "/platos/" + platoId);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        connection.setDoOutput(true); // 👈 IMPORTANTE en algunos casos

        agregarToken(connection);

        connection.setRequestProperty("Accept", "application/json");

        int code = connection.getResponseCode();

        InputStream stream = (code >= 400)
                ? connection.getErrorStream()
                : connection.getInputStream();

        String response = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        if (code >= 400) {
            throw new RuntimeException("Error eliminando plato: " + response);
        }
    }

    // =========================
    // MODIFICAR PLATO
    // =========================
    public PlatoResponseDTO modificarPlato(Long idRestaurante,
                                           Long platoId,
                                           PlatoUpdateDTO dto) throws Exception {

        URL url = new URL(API_URL + "/" + idRestaurante + "/platos/" + platoId);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        agregarToken(connection);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        return leerRespuesta(connection, mapper);
    }

    // =========================
    // HELPERS
    // =========================

    private void agregarToken(HttpURLConnection connection) {
        String token = SessionManager.getAccessToken();
        if (token != null) {
            connection.setRequestProperty("Authorization", "Bearer " + token);
        }
    }

    private PlatoResponseDTO leerRespuesta(HttpURLConnection connection,
                                           ObjectMapper mapper) throws Exception {

        int status = connection.getResponseCode();

        InputStream stream = (status >= 400)
                ? connection.getErrorStream()
                : connection.getInputStream();

        String response = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        if (status >= 400) {
            throw new RuntimeException("Error backend: " + response);
        }

        return mapper.readValue(response, PlatoResponseDTO.class);
    }

    private String leerError(HttpURLConnection connection) throws Exception {
        InputStream err = connection.getErrorStream();
        return err != null ? new String(err.readAllBytes(), StandardCharsets.UTF_8) : "Sin detalle";
    }
    public void iniciarPromocion(Long idRestaurante,
                                 Long platoId,
                                 PromocionRequestDTO dto) throws Exception {

        URL url = new URL(API_URL + "/" + idRestaurante + "/platos/" + platoId + "/promocion/iniciar");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        agregarToken(connection);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int status = connection.getResponseCode();

        if (status >= 400) {
            InputStream err = connection.getErrorStream();
            String response = new String(err.readAllBytes(), StandardCharsets.UTF_8);
            throw new RuntimeException("Error iniciando promoción: " + response);
        }
    }
    public void terminarPromocion(Long idRestaurante,
                                  Long platoId) throws Exception {

        URL url = new URL(API_URL + "/" + idRestaurante + "/platos/" + platoId + "/promocion/terminar");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        agregarToken(connection);

        int status = connection.getResponseCode();

        if (status >= 400) {
            InputStream err = connection.getErrorStream();
            String response = new String(err.readAllBytes(), StandardCharsets.UTF_8);
            throw new RuntimeException("Error terminando promoción: " + response);
        }
    }
}