package grupo.proyecto.Models.dto.response;

import grupo.proyecto.Enums.Etiquetas;

import java.math.BigDecimal;
import java.util.List;

public record PlatoResponseDTO(String nombreRestaurant,String nombre, BigDecimal precio, List<Etiquetas> etiquetas,boolean disponible) {
}
