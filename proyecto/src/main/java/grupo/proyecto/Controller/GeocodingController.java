package grupo.proyecto.Controller;

import grupo.proyecto.Service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/geocoding")
@RequiredArgsConstructor
@Tag(name = "Geocodificación", description = "Integración con API externa (Nominatim) para obtener coordenadas geográficas")
public class GeocodingController {

    private final GeocodingService geocodingService;

    @GetMapping
    @Operation(summary = "Convertir dirección a coordenadas", description = "Recibe una dirección en formato texto y devuelve un array con la Latitud y Longitud correspondiente.")
    public Double[] obtenerCoordenadas(@RequestParam String direccion) {
        return geocodingService.obtenerCoordenadas(direccion);
    }
}