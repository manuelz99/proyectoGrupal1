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
        URL url = new URL(API_URL + "/all?id=" + idRestaurante);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.toString(), new TypeReference<List<PlatoResponseDTO>>() {});
    }

    public PlatoResponseDTO crearPlato(PlatoRequestDTO platoDTO) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(platoDTO);

        OutputStream os = connection.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        os.close();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return mapper.readValue(response.toString(), PlatoResponseDTO.class);
    }
    public void eliminarPlato(Long id) throws Exception {

        URL url =
                new URL(
                        API_URL + "/" + id
                );

        HttpURLConnection connection =
                (HttpURLConnection)
                        url.openConnection();

        connection.setRequestMethod(
                "DELETE"
        );

        int responseCode =
                connection.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_NO_CONTENT
                && responseCode != HttpURLConnection.HTTP_OK) {

            throw new RuntimeException(
                    "Error eliminando plato. Código: "
                            + responseCode
            );
        }
    }
    public PlatoResponseDTO modificarPlato(
            Long id,
            PlatoUpdateDTO dto
    ) throws Exception {

        URL url =
                new URL(
                        API_URL + "/" + id
                );

        HttpURLConnection connection =
                (HttpURLConnection)
                        url.openConnection();

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        connection.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        ObjectMapper mapper =
                new ObjectMapper();

        String json =
                mapper.writeValueAsString(dto);

        try (OutputStream os =
                     connection.getOutputStream()) {

            os.write(json.getBytes());
        }

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()
                        )
                );

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {

            response.append(line);
        }

        return mapper.readValue(
                response.toString(),
                PlatoResponseDTO.class
        );
    }
}