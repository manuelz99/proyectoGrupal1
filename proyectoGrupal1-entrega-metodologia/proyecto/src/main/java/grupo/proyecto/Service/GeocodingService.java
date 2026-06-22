package grupo.proyecto.Service;

import grupo.proyecto.Models.dto.request.NominatimResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    public Double[] obtenerCoordenadas(String direccion) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "MiProyectoSpringBoot");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url =
                "https://nominatim.openstreetmap.org/search?q="
                        + direccion.replace(" ", "+")
                        + "&format=jsonv2&limit=1";

        ResponseEntity<NominatimResponse[]> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        NominatimResponse[].class
                );

        NominatimResponse[] body = response.getBody();

        if (body == null || body.length == 0) {
            throw new RuntimeException("Dirección no encontrada");
        }

        Double latitud = Double.parseDouble(body[0].getLat());
        Double longitud = Double.parseDouble(body[0].getLon());

        return new Double[]{latitud, longitud};
    }
}