package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.request.LoginRequestDTO;
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

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> encontrarCrear(@Valid @RequestBody CrearUsuarioRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(dto));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> encontrarPorEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                usuarioService.encontrarPorEmail(email)
        );
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(
            @RequestBody LoginRequestDTO dto
    ) {
        UsuarioResponseDTO usuario = usuarioService.login(
                dto.getEmail(),
                dto.getPassword()
        );

        return ResponseEntity.ok(usuario);
    }
}
