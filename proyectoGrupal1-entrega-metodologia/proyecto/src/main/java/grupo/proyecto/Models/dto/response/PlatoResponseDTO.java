package grupo.proyecto.Models.dto.response;

import grupo.proyecto.Enums.Etiquetas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatoResponseDTO {

    private Long id;
    private String nombreRestaurant;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private List<Etiquetas> etiquetas;
    private boolean disponible;
}