package grupo.proyecto.Models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RestauranteRequestDTO {

    @NotBlank(message = "La direccion del restaurant no puede ser nula.")
    private String direccion;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La especialidad es obligatoria")

    @Size(min = 3, message = "La especialidad debe tener por lo menos 3 caracteres")
    private String especialidad;

    @NotNull(message = "Debes indicar si tiene opciones saludables o no")
    private Boolean ofreceOpcionesSaludables;
}
