package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.ReseñaPlatoRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.ReseñaPlatoResponseDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Service.ReseñaPlatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reseñas-platos")
@RequiredArgsConstructor
@Tag(name = "Controller de reseñas platos")
public class ReseñaPlatoController {
    private final ReseñaPlatoService reseñaPlatoService;
    @PostMapping
    public ResponseEntity<ReseñaPlatoResponseDTO> crear(@Valid @RequestBody ReseñaPlatoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(reseñaPlatoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReseñaPlatoResponseDTO>> listar() {

        return ResponseEntity.ok(reseñaPlatoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReseñaPlatoResponseDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(reseñaPlatoService.buscarPorId(id));
    }

}
