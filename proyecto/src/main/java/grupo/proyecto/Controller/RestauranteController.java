package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private RestauranteService service;

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> crear(@Valid @RequestBody RestauranteRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearRestaurante(dto));
    }

    /*
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listar(@RequestParam(required = false) String nombre,
                                                               @RequestParam(required = false) String direccion) {

        return ResponseEntity.ok(service.listar(nombre, direccion));
    }
     */

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
