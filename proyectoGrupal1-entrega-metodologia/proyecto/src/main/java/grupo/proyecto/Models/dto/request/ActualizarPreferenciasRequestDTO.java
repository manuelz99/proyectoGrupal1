package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarPreferenciasRequestDTO {
    @NotEmpty(message = "Por lo menos una etiqueta debe ser ingresada")
    private List<@NotNull Etiquetas> etiquetas;
}
