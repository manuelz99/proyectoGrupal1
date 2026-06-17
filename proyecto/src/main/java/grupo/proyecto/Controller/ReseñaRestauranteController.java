package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.ReseñaRestauranteRequestDTO;

import grupo.proyecto.Models.dto.response.ReseñaRestaurtanteResponseDTO;
import grupo.proyecto.Service.ReseñaRestauranteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reseñas-restaurantes")
@RequiredArgsConstructor
@Tag(name = "Gestión de reseñas de restaurantes")
public class ReseñaRestauranteController {

    private final ReseñaRestauranteService reseñaRestauranteService;

    // =========================
    // CREAR RESEÑA
    // =========================
    @PostMapping
    public ResponseEntity<ReseñaRestaurtanteResponseDTO> crear(
            @Valid @RequestBody ReseñaRestauranteRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reseñaRestauranteService.crear(dto));
    }

    // =========================
    // LISTAR TODAS
    // =========================
    @GetMapping
    public ResponseEntity<List<ReseñaRestaurtanteResponseDTO>> listar() {

        return ResponseEntity.ok(
                reseñaRestauranteService.listar()
        );
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<ReseñaRestaurtanteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reseñaRestauranteService.buscarPorId(id)
        );
    }

    // =========================
    // LISTAR POR RESTAURANTE
    // =========================
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<ReseñaRestaurtanteResponseDTO>> listarPorRestaurante(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reseñaRestauranteService.listarPorRestaurante(id)
        );
    }
}