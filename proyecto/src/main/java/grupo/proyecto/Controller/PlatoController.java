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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platos")
@Tag(name = "Gestión de platos")
@RequiredArgsConstructor
public class PlatoController { //Este controller ahora es obsoleto pero lo dejo por ahora por las dudas

    private final PlatoService platoService;

    // =========================
    // CREAR
    // =========================
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

    // =========================
    // BORRAR
    // =========================
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