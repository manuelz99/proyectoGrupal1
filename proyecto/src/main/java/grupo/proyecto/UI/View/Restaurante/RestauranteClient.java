package grupo.proyecto.UI.View.Restaurante;

import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteUpdateDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestauranteClient {

    private static final String API_URL =
            "http://localhost:8080/api/v1/restaurantes";

    private final ObjectMapper mapper =
            new ObjectMapper();


    public RestauranteResponseDTO registrar(
            RestauranteRequestDTO dto
    ) throws Exception {

        URL url =
                new URL(API_URL);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        connection.setDoOutput(true);


        String json =
                mapper.writeValueAsString(dto);


        try(OutputStream os =
                    connection.getOutputStream()) {

            os.write(json.getBytes());
        }

        int status =
                connection.getResponseCode();

        if(status != 201) {

            throw new RuntimeException(
                    "Error HTTP: " + status
            );
        }

        // leer respuesta
        return mapper.readValue(
                connection.getInputStream(),
                RestauranteResponseDTO.class
        );
    }


    public RestauranteResponseDTO obtenerRestaurante(Long id)
            throws Exception {

        URL url =
                new URL(API_URL + "/" + id);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                RestauranteResponseDTO.class
        );
    }


    public RestauranteResponseDTO buscarPorNombre(String nombre)
            throws Exception {

        URL url =
                new URL(
                        API_URL +
                                "/buscar/nombre?nombre=" +
                                nombre
                );

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                RestauranteResponseDTO.class
        );
    }


    public RestauranteResponseDTO buscarPorEmail(String email)
            throws Exception {

        URL url =
                new URL(
                        API_URL +
                                "/buscar/email?email=" +
                                email
                );

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                RestauranteResponseDTO.class
        );
    }
    public RestauranteResponseDTO modificarRestaurante(
            Long id,
            RestauranteUpdateDTO dto
    ) throws Exception {

        URL url =
                new URL(
                        API_URL + "/{id}" + id
                );

        HttpURLConnection conn =
                (HttpURLConnection)
                        url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        ObjectMapper mapper =
                new ObjectMapper();

        try(OutputStream os =
                    conn.getOutputStream()) {

            mapper.writeValue(os, dto);
        }

        if(conn.getResponseCode() != 200) {

            throw new RuntimeException(
                    "Error HTTP: "
                            + conn.getResponseCode()
            );
        }

        return mapper.readValue(
                conn.getInputStream(),
                RestauranteResponseDTO.class
        );
    }
    public RestauranteResponseDTO login(String email, String password) throws Exception {

        URL url = new URL(API_URL + "/login");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);


        class LoginRequest {
            public String email;
            public String password;

            LoginRequest(String email, String password) {
                this.email = email;
                this.password = password;
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(new LoginRequest(email, password));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        int status = conn.getResponseCode();

        if (status != 200) {
            throw new RuntimeException("Login incorrecto");
        }

        return mapper.readValue(
                conn.getInputStream(),
                RestauranteResponseDTO.class
        );
    }

}