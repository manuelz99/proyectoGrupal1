package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.PlatoUpdateDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Service.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platos")
@Tag(name = "Gestión de platos")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;

    // =========================
    // CREAR
    // =========================
    @PostMapping
    @Operation(summary = "Agregar plato")
    public ResponseEntity<PlatoResponseDTO> crear(
            @Valid @RequestBody PlatoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(platoService.crearPlato(dto));
    }

    // =========================
    // LISTAR POR RESTAURANTE
    // =========================
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorRestaurante(
            @PathVariable Long id) {

        return ResponseEntity.ok(platoService.listarPorRestaurante(id));
    }

    // =========================
    // LISTAR TODOS (por restaurante o admin)
    // =========================
    @GetMapping("/all")
    @Operation(summary = "Listar todos los platos de un restaurante")
    public ResponseEntity<List<PlatoResponseDTO>> listar(
            @RequestParam Long id) {

        return ResponseEntity.ok(
                platoService.listaTodosLosPlatos(id)
        );
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @GetMapping("/{id}")
    @Operation(summary = "Buscar plato por id")
    public ResponseEntity<PlatoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                platoService.hacerDTO(id)
        );
    }

    // =========================
    // MODIFICAR
    // =========================
    @PutMapping("/{id}")
    @Operation(summary = "Modificar plato")
    public ResponseEntity<PlatoResponseDTO> modificar(
            @PathVariable Long id,
            @RequestBody PlatoUpdateDTO dto) {

        return ResponseEntity.ok(
                platoService.modificarPlato(id, dto)
        );
    }

    // =========================
    // BORRAR
    // =========================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plato")
    public ResponseEntity<Void> borrar(
            @PathVariable Long id) {

        platoService.eliminar_plato(id);
        return ResponseEntity.noContent().build();
    }
}