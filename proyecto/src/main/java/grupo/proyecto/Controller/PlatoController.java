package grupo.proyecto.Controller;

import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.PlatoUpdateDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Service.PlatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platos")
@Tag(name = "Gestion de platos",description = "Algoritmos para busqueda de platos por distintos valores")
@RequiredArgsConstructor
public class PlatoController {
    private final PlatoService platoService;

    @PostMapping
    public ResponseEntity<PlatoResponseDTO> crear(@Valid @RequestBody PlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platoService.crearPlato(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlatoResponseDTO>> listar(@RequestParam Long id) {
        return ResponseEntity.ok(platoService.listaTodosLosPlatos(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(platoService.hacerDTO(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        platoService.eliminar_plato(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> modificar(@PathVariable Long id, @RequestBody PlatoUpdateDTO dto) {

        return ResponseEntity.ok(platoService.modificarPlato(id, dto)
        );
    }
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorRestaurante(@PathVariable Long id) {
        return ResponseEntity.ok(platoService.listarPorRestaurante(id));
    }
}
