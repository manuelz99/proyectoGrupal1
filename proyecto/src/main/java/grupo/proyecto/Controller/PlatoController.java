package grupo.proyecto.Controller;

import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.ErrorResponseDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Service.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/v1/platos")
@Tag(name = "Gestion de platos",description = "Algoritmos para busqueda de platos por disntintos valores")
@RequiredArgsConstructor
public class PlatoController {
    private final PlatoService platoService;

    @PostMapping
    @Operation(summary = "Agrega plato")
    public ResponseEntity<PlatoResponseDTO> crear(@Valid @RequestBody PlatoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(platoService.crearPlato(dto));
    }

    @GetMapping("/all")
    @Operation(summary = "Lista todos los platos", description = "Metodo para admin, no devuelve dto")

    public ResponseEntity<List<Plato>> listar(@RequestParam Long id) {

        return ResponseEntity.ok(platoService.listaTodosLosPlatos(id));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Busca plato", description = "devuelve DTOResponse")
    public ResponseEntity<PlatoResponseDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(platoService.hacerDTO(id));
    }
    @DeleteMapping
    @Operation(summary = "Borra plato",description = "")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        platoService.eliminar_plato(id);
        return ResponseEntity.noContent().build();
    }
}
