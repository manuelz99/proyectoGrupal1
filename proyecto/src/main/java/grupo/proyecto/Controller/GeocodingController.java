package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.DireccionRequestDTO;
import grupo.proyecto.Service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geocoding")
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingService geocodingService;

    @PostMapping
    public Double[] obtenerCoordenadas(
            @RequestBody DireccionRequestDTO dto
    ) {

        return geocodingService.obtenerCoordenadas(
                dto.getDireccion()
        );
    }
}