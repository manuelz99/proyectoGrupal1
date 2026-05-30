package grupo.proyecto.Models.dto.response;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciasResponseDTO{
    private Set<Etiquetas> etiquetas;
}
