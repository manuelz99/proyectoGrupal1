package grupo.proyecto.Controller;

import grupo.proyecto.Models.dto.AuthDTO;
import grupo.proyecto.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthDTO.AuthResponse> register(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión",
            description = "Autentica al usuario y devuelve access token + refresh token")
    public ResponseEntity<AuthDTO.AuthResponse> login(@Valid @RequestBody AuthDTO.AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refrescar access token",
            description = "Genera un nuevo access token usando el refresh token sin reenviar credenciales")
    public ResponseEntity<AuthDTO.AuthResponse> refresh(@Valid @RequestBody AuthDTO.RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshAccessToken(request.refreshToken()));
    }
}
