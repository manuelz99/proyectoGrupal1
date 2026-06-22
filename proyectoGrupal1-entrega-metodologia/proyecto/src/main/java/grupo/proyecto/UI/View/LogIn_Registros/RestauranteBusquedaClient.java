package grupo.proyecto.UI.View.LogIn_Registros;

import grupo.proyecto.Models.dto.request.*;
import grupo.proyecto.Models.dto.response.*;
import grupo.proyecto.Sesion.SessionManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestauranteBusquedaClient {

    private static final String URL =
            "http://localhost:8080/api/v1/restaurantes/cercanos";

    private HttpHeaders buildHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String token = SessionManager.getAccessToken();

        System.out.println("TOKEN EN SESSION: " + token);

        if (token != null && !token.isEmpty()) {
            headers.setBearerAuth(token);
        }

        return headers;
    }

    public List<RestauranteResponseDTO> buscarCercanos(RestauranteCercanoRequestDTO dto) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RestauranteCercanoRequestDTO> request =
                new HttpEntity<>(dto, buildHeaders());

        ResponseEntity<RestauranteResponseDTO[]> response =
                restTemplate.exchange(
                        URL,
                        HttpMethod.POST,
                        request,
                        RestauranteResponseDTO[].class
                );

        return Arrays.asList(response.getBody());
    }

    public RestauranteResponseDTO buscarPorId(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(
                "http://localhost:8080/api/v1/restaurantes/" + id,
                RestauranteResponseDTO.class
        );
    }

    public List<PlatoResponseDTO> obtenerPlatos(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<PlatoResponseDTO>> response =
                restTemplate.exchange(
                        "http://localhost:8080/api/v1/restaurantes/" + id + "/platos",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PlatoResponseDTO>>() {}
                );

        return response.getBody();
    }

    public RestauranteResponseDTO buscarEntidadPorNombre(String nombre) {

        RestTemplate restTemplate = new RestTemplate();

        String url =
                "http://localhost:8080/api/v1/restaurantes/buscar/nombre?nombre=" + nombre;

        return restTemplate.getForObject(url, RestauranteResponseDTO.class);
    }

    public List<ReseñaRestaurtanteResponseDTO> obtenerReseñasRestaurante(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<ReseñaRestaurtanteResponseDTO>> response =
                restTemplate.exchange(
                        "http://localhost:8080/api/v1/reseñas-restaurantes/restaurante/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(buildHeaders()),
                        new ParameterizedTypeReference<List<ReseñaRestaurtanteResponseDTO>>() {}
                );

        return response.getBody();
    }

    public void crearReseñaRestaurante(ReseñaRestauranteRequestDTO dto) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ReseñaRestauranteRequestDTO> request =
                new HttpEntity<>(dto, buildHeaders());

        restTemplate.postForEntity(
                "http://localhost:8080/api/v1/reseñas-restaurantes",
                request,
                Void.class
        );
    }
}