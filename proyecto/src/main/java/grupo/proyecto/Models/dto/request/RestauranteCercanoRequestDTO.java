package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteCercanoRequestDTO {

    private Double latitud;
    private Double longitud;
    private Double radioMetros;
    private Etiquetas especialidad;
}