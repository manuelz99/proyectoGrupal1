package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteRequestDTO {

    @NotBlank(message = "La direccion del restaurant no puede ser nula.")
    private String direccion;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotEmpty(message = "Debe ingresar al menos una especialidad")
    private List<Etiquetas> especialidades;
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank(message = "La contraseña es obligatorio")
    private String password;
}
