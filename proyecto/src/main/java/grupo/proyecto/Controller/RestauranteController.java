package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.*;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Service.PlatoService;
import grupo.proyecto.Service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;
    private final PlatoService platoService;

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
    @PreAuthorize("#id == authentication.principal.restaurante.id")
    public ResponseEntity<RestauranteResponseDTO> modificar(
            @PathVariable Long id,
            @RequestBody RestauranteUpdateDTO dto
    ) {

        return ResponseEntity.ok(
                service.modificarRestaurante(id, dto)
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

    @PostMapping("/{id}/platos")
    @Operation(summary = "Agregar plato")
    @PreAuthorize("#id == authentication.principal.restaurante.id")
    public ResponseEntity<PlatoResponseDTO> crear(
            @PathVariable Long id,
            @Valid @RequestBody PlatoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(platoService.crearPlato(dto, id));
    }

    @GetMapping("/{id}/platos")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorRestaurante(
            @PathVariable Long id) {

        return ResponseEntity.ok(platoService.listarPorRestaurante(id));
    }

    @GetMapping("/platos/all")
    @Operation(summary = "Listar todos los platos de un restaurante")
    public ResponseEntity<List<PlatoResponseDTO>> listar(
            @RequestParam Long id) {

        return ResponseEntity.ok(
                platoService.listaTodosLosPlatos(id)
        );
    }

    @GetMapping("/platos/{id}")
    @Operation(summary = "Buscar plato por id")
    public ResponseEntity<PlatoResponseDTO> buscarPlatoPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                platoService.hacerDTO(id)
        );
    }

    @PutMapping("{id}/platos/{platoId}")
    @Operation(summary = "Modificar plato")
    @PreAuthorize("#id == authentication.principal.restaurante.id")
    public ResponseEntity<PlatoResponseDTO> modificar(
            @PathVariable Long platoId,
            @PathVariable Long id,
            @RequestBody PlatoUpdateDTO dto) {

        return ResponseEntity.ok(
                platoService.modificarPlato(id, platoId, dto)
        );
    }

    @DeleteMapping("{id}/platos/{platoId}")
    @Operation(summary = "Eliminar plato")
    @PreAuthorize("#id == authentication.principal.restaurante.id")
    public ResponseEntity<Void> borrar(
            @PathVariable Long platoId,
            @PathVariable Long id) {

        platoService.eliminar_plato(id, platoId);
        return ResponseEntity.noContent().build();
    }
}
