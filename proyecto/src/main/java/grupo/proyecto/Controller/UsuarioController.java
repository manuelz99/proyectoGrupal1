package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.request.LoginRequestDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;

import grupo.proyecto.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> encontrarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.encontrarPorId(id));
    }

    //este metodo "crear" no servia ya que existe Registe en AuthController

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> encontrarPorEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(usuarioService.encontrarPorEmail(email));
    }

    @PatchMapping("/{id}/preferencias/agregar")
    @PreAuthorize("#id == authentication.principal.usuario.id") //Esto verifica que el endpoint solo sea usado por el usuario con id correspondiente al que inició sesión
    public ResponseEntity<PreferenciasResponseDTO> agregarPreferencias(@Valid @RequestBody ActualizarPreferenciasRequestDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.agregarPreferencias(dto, id));
    }

    @PreAuthorize("#id == authentication.principal.usuario.id")
    @PatchMapping("/{id}/preferencias/eliminar")
    public ResponseEntity<PreferenciasResponseDTO> eliminarPreferencias(@Valid @RequestBody ActualizarPreferenciasRequestDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.eliminarPreferencias(dto, id));
    }

    @PreAuthorize("#id == authentication.principal.usuario.id")
    @GetMapping("/{id}/preferencias")
    public ResponseEntity<PreferenciasResponseDTO> listarPreferencias(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.listarPreferencias(id));
    }

    @PreAuthorize("#id == authentication.principal.usuario.id")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarPerfil(@Valid @RequestBody CrearUsuarioRequestDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.actualizarPerfil(dto, id));
    }
}
