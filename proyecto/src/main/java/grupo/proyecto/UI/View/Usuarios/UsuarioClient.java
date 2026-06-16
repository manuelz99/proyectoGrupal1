package grupo.proyecto.UI.View.Usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsuarioClient {

    private static final String BASE_URL =
            "http://localhost:8080/api/v1/usuarios";

    private final ObjectMapper mapper =
            new ObjectMapper();


    public void registrar(CrearUsuarioRequestDTO dto) throws Exception {

        URL url = new URL(BASE_URL);

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = mapper.writeValueAsString(dto);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        int responseCode = conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException(
                    "Error al registrar usuario. Código: " + responseCode
            );
        }
    }


    public UsuarioResponseDTO buscarPorEmail(String email) throws Exception {

        URL url = new URL(BASE_URL + "/buscar/email?email=" + email);

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return mapper.readValue(
                conn.getInputStream(),
                UsuarioResponseDTO.class
        );
    }


    public UsuarioResponseDTO login(String email, String password) throws Exception {

        URL url = new URL(BASE_URL + "/login");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

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

        String json = mapper.writeValueAsString(
                new LoginRequest(email, password)
        );

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        int status = conn.getResponseCode();


        if (status != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        return mapper.readValue(
                conn.getInputStream(),
                UsuarioResponseDTO.class
        );
    }
}