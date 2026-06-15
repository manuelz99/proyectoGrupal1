package grupo.proyecto.Models.dto.response;

import grupo.proyecto.Enums.Etiquetas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public record PlatoResponseDTO(
        Long id,
        String nombreRestaurant,
        String nombre,
        String descripcion,
        BigDecimal precio,
        List<Etiquetas> etiquetas,
        boolean disponible

) {
}
