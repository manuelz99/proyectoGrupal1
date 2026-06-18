package grupo.proyecto.UI.View.Restaurante;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.PlatoUpdateDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PlatoClient {

    private static final String API_URL = "http://localhost:8080/api/v1/platos";

    public List<PlatoResponseDTO> obtenerPlatos(Long idRestaurante) throws Exception {

        URL url = new URL(API_URL + "/restaurante/" + idRestaurante);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();

        InputStream stream;

        if (status >= 400) {
            stream = connection.getErrorStream();
        } else {
            stream = connection.getInputStream();
        }

        String response = new String(stream.readAllBytes());

        if (status >= 400) {
            throw new RuntimeException("Error backend: " + response);
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<PlatoResponseDTO>>() {
        });
    }

    public PlatoResponseDTO crearPlato(PlatoRequestDTO platoDTO) throws Exception {
        // Nueva URL: /api/v1/platos/{idRestaurante}/platos
        URL url = new URL(API_URL + "/" + platoDTO.getIdRestaurante() + "/platos");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        if (grupo.proyecto.Sesion.SessionManager.getAccessToken() != null) {
            connection.setRequestProperty("Authorization", "Bearer " + grupo.proyecto.Sesion.SessionManager.getAccessToken());
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(platoDTO);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes());
        }

        int status = connection.getResponseCode();
        if (status >= 400) {
            InputStream errorStream = connection.getErrorStream();
            String errorBody = (errorStream != null) ? new String(errorStream.readAllBytes()) : "Sin detalle";
            throw new RuntimeException("Error " + status + " del servidor:\n" + errorBody);
        }

        return mapper.readValue(connection.getInputStream(), PlatoResponseDTO.class);
    }

    // A este método le sumamos el parámetro idRestaurante
    public void eliminarPlato(Long platoId, Long idRestaurante) throws Exception {
        // Nueva URL: /api/v1/platos/{idRestaurante}/platos/{platoId}
        URL url = new URL(API_URL + "/" + idRestaurante + "/platos/" + platoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        if (grupo.proyecto.Sesion.SessionManager.getAccessToken() != null) {
            connection.setRequestProperty("Authorization", "Bearer " + grupo.proyecto.Sesion.SessionManager.getAccessToken());
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_NO_CONTENT && responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Error eliminando plato. Código: " + responseCode);
        }
    }

    // A este método le sumamos el parámetro idRestaurante
    public PlatoResponseDTO modificarPlato(Long platoId, Long idRestaurante, PlatoUpdateDTO dto) throws Exception {
        // Nueva URL: /api/v1/platos/{idRestaurante}/platos/{platoId}
        URL url = new URL(API_URL + "/" + idRestaurante + "/platos/" + platoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        if (grupo.proyecto.Sesion.SessionManager.getAccessToken() != null) {
            connection.setRequestProperty("Authorization", "Bearer " + grupo.proyecto.Sesion.SessionManager.getAccessToken());
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes());
        }

        int status = connection.getResponseCode();
        if (status >= 400) {
            InputStream errorStream = connection.getErrorStream();
            String errorBody = (errorStream != null) ? new String(errorStream.readAllBytes()) : "Sin detalle";
            throw new RuntimeException("Error " + status + " del servidor:\n" + errorBody);
        }

        return mapper.readValue(connection.getInputStream(), PlatoResponseDTO.class);
    }
}