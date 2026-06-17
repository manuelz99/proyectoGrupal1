package grupo.proyecto.Models.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReseñaPlatoRequestDTO {

    @NotBlank(message = "La descripcion no puede ser vacia.")
    @Column(nullable = false)
    private String descripcion;

    @Min(0)
    @Max(10)
    @NotNull(message = "La calificacion no puede ser nula")
    @Column(nullable = false)
    private Integer calificacion;
}
