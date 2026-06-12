package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.FavoritosResponseDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> encontrarPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.encontrarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar(){
        return ResponseEntity.ok(usuarioService.listar());
    }


    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> encontrarPorId(@Valid @RequestBody CrearUsuarioRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(dto));
    }

    @PatchMapping("/{id}/preferencias/agregar")
    public ResponseEntity<PreferenciasResponseDTO> agregarPreferencias(@Valid @RequestBody ActualizarPreferenciasRequestDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.agregarPreferencias(dto, id));
    }

    @PatchMapping("/{id}/preferencias/eliminar")
    public ResponseEntity<PreferenciasResponseDTO> eliminarPreferencias(@Valid @RequestBody ActualizarPreferenciasRequestDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.eliminarPreferencias(dto, id));
    }

    @PutMapping("/{id}/")
    public ResponseEntity<UsuarioResponseDTO> actualizarPerfil(@Valid @RequestBody CrearUsuarioRequestDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.actualizarPerfil(dto, id));
    }

    @PatchMapping("/{usuarioId}/favoritos/{restauranteId}")
    public ResponseEntity<Void> agregarFavorito(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{usuarioId}/favoritos/{restauranteId}")
    public ResponseEntity<Void> eliminarFavorito(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/favoritos/")
    public ResponseEntity<FavoritosResponseDTO> mostrarFavoritos(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.verFavoritos(id));
    }
}
