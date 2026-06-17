package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.LoginRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteCercanoRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteUpdateDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> crear(@Valid @RequestBody RestauranteRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearRestaurante(dto));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listar(@RequestParam(required = false) String nombre,
                                                               @RequestParam(required = false) String direccion) {

        return ResponseEntity.ok(service.listar(nombre, direccion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @GetMapping("/buscar/nombre")
    public ResponseEntity<RestauranteResponseDTO> buscarPorNombre(
            @RequestParam String nombre
    ) {

        return ResponseEntity.ok(
                service.buscarPorNombre(nombre)
        );
    }
    @GetMapping("/buscar/email")
    public ResponseEntity<RestauranteResponseDTO> buscarPorEmail(
            @RequestParam String email
    ) {

        return ResponseEntity.ok(
                service.buscarPorEmail(email)
        );
    }
    /*@GetMapping("/me")
    public ResponseEntity<RestauranteResponseDTO> miRestaurante(Principal principal) {
        return ResponseEntity.ok(
                service.buscarPorEmail(principal.getName())
        );
    }*/
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> modificar(
            @PathVariable Long id,
            @RequestBody RestauranteUpdateDTO dto
    ) {

        return ResponseEntity.ok(
                service.modificarRestaurante(
                        id,
                        dto
                )
        );
    }
    @PostMapping("/cercanos")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarCercanos(
            @RequestBody RestauranteCercanoRequestDTO dto
    ) {

        return ResponseEntity.ok(
                service.buscarCercanos(dto)
        );
    }
}
