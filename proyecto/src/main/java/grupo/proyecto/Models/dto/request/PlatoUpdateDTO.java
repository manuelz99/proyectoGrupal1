package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlatoUpdateDTO {

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private List<Etiquetas> etiquetas;
    private Boolean disponible;
}