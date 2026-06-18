package grupo.proyecto.UI.View;

import org.springframework.web.client.RestTemplate;

public class GeocodingClient {

    private static final String URL = "http://localhost:8080/api/v1/geocoding";

    public Double[] obtenerCoordenadas(String direccion) {

        RestTemplate restTemplate = new RestTemplate();

        // Armamos la URL pegándole el parámetro al final
        String urlConParametro = URL + "?direccion=" + direccion.replace(" ", "+");

        // Usamos getForObject que es la forma más directa de hacer un GET
        return restTemplate.getForObject(urlConParametro, Double[].class);
    }
}