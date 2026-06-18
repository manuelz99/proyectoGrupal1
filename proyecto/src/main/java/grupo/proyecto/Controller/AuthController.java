package grupo.proyecto.Controller;

import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.dto.request.LoginRequestDTO;
import grupo.proyecto.Models.dto.request.RegisterRequestDTO;
import grupo.proyecto.Models.dto.response.AuthResponseDTO;
import grupo.proyecto.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints de registro, login y refresco de token")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario con rol USER y devuelve los tokens")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(
                authService.authenticate(request.getEmail(), request.getPassword())
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {

        CredentialsEntity user = (CredentialsEntity) auth.getPrincipal();

        String role = user.getRoles()
                .stream()
                .findFirst()
                .map(r -> r.getRole().name())
                .orElse("UNKNOWN");

        // Buscamos el ID real dependiendo de si es Restaurante o Usuario
        Long id = null;
        if (role.equals("ROLE_RESTAURANTE") && user.getRestaurante() != null) {
            id = user.getRestaurante().getId();
        } else if (role.equals("ROLE_USER") && user.getUsuario() != null) {
            id = user.getUsuario().getId();
        }

        return ResponseEntity.ok(
                Map.of(
                        "email", user.getEmail(),
                        "role", role,
                        "id", id != null ? id : -1
                )
        );
    }
}
