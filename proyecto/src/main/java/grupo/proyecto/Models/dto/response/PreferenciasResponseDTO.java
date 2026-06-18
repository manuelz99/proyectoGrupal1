package grupo.proyecto.Models.dto.response;

import grupo.proyecto.Enums.Etiquetas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciasResponseDTO {
    private List<Etiquetas> etiquetas;
}
