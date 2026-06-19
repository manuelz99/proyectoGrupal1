package grupo.proyecto.Models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "Debe ingresar un correo valido")
    private String email;

    @Size(min = 6, message = "La contrasenia no puede ser menor a 6 caracteres")
    @NotBlank(message = "La contrasenia no puede ser nula")
    private String password;
}
