package grupo.proyecto.UI.View;

import grupo.proyecto.Models.dto.request.DireccionRequestDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class GeocodingClient {

    private static final String URL =
            "http://localhost:8080/api/v1/geocoding";

    public Double[] obtenerCoordenadas(
            String direccion
    ) {

        RestTemplate restTemplate =
                new RestTemplate();

        DireccionRequestDTO dto =
                new DireccionRequestDTO();

        dto.setDireccion(direccion);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<DireccionRequestDTO> request =
                new HttpEntity<>(
                        dto,
                        headers
                );

        ResponseEntity<Double[]> response =
                restTemplate.exchange(
                        URL,
                        HttpMethod.POST,
                        request,
                        Double[].class
                );

        return response.getBody();
    }
}