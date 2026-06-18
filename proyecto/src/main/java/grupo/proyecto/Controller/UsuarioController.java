package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Gestión de Usuarios", description = "Endpoints para la consulta y modificación de perfiles de usuario y sus preferencias")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene los datos públicos de un usuario específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> encontrarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.encontrarPorId(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuario por Email", description = "Obtiene los datos de un usuario filtrando por su correo electrónico exacto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> encontrarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.encontrarPorEmail(email));
    }

    @PatchMapping("/{id}/preferencias/agregar")
    @PreAuthorize("#id == authentication.principal.usuario.id")
    @Operation(summary = "Agregar preferencias alimenticias", description = "Añade nuevas etiquetas de preferencias al perfil del usuario logueado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferencias agregadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados"),
            @ApiResponse(responseCode = "401", description = "No autorizado (Falta token)"),
            @ApiResponse(responseCode = "403", description = "Prohibido (Intento de modificar a otro usuario)")
    })
    public ResponseEntity<PreferenciasResponseDTO> agregarPreferencias(
            @Valid @RequestBody ActualizarPreferenciasRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.agregarPreferencias(dto, id));
    }

    @PatchMapping("/{id}/preferencias/eliminar")
    @PreAuthorize("#id == authentication.principal.usuario.id")
    @Operation(summary = "Eliminar preferencias alimenticias", description = "Remueve etiquetas de preferencias del perfil del usuario logueado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferencias eliminadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados"),
            @ApiResponse(responseCode = "401", description = "No autorizado (Falta token)"),
            @ApiResponse(responseCode = "403", description = "Prohibido (Intento de modificar a otro usuario)")
    })
    public ResponseEntity<PreferenciasResponseDTO> eliminarPreferencias(
            @Valid @RequestBody ActualizarPreferenciasRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.eliminarPreferencias(dto, id));
    }

    @GetMapping("/{id}/preferencias")
    @PreAuthorize("#id == authentication.principal.usuario.id")
    @Operation(summary = "Listar preferencias", description = "Obtiene la lista completa de etiquetas de preferencias alimenticias del usuario logueado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de preferencias obtenida"),
            @ApiResponse(responseCode = "401", description = "No autorizado (Falta token)"),
            @ApiResponse(responseCode = "403", description = "Prohibido (Intento de consultar a otro usuario)")
    })
    public ResponseEntity<PreferenciasResponseDTO> listarPreferencias(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.listarPreferencias(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.usuario.id")
    @Operation(summary = "Actualizar perfil", description = "Modifica los datos personales (nombre, dirección, etc.) del usuario logueado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados"),
            @ApiResponse(responseCode = "401", description = "No autorizado (Falta token)"),
            @ApiResponse(responseCode = "403", description = "Prohibido (Intento de modificar a otro usuario)")
    })
    public ResponseEntity<UsuarioResponseDTO> actualizarPerfil(
            @Valid @RequestBody CrearUsuarioRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(dto, id));
    }
}