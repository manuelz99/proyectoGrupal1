package grupo.proyecto.Models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDTO {

    public record AuthRequest(
            @NotBlank(message = "El email no puede estar vacío")
            @Email(message = "Formato de email inválido")
            String username,

            @NotBlank(message = "La contraseña no puede estar vacía")
            String password
    ) {}

    public record AuthResponse(
            String accessToken,
            String refreshToken
    ) {}

    public record RegisterRequest(
            @NotBlank(message = "El nombre no puede estar vacío")
            String nombre,

            @NotBlank(message = "El email no puede estar vacío")
            @Email(message = "Formato de email inválido")
            String email,

            @NotBlank(message = "La contraseña no puede estar vacía")
            String password
    ) {}

    public record RefreshTokenRequest(
            @NotBlank(message = "El refresh token no puede estar vacío")
            String refreshToken
    ) {}
}
