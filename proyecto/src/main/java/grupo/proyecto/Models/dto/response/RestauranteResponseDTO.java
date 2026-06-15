package grupo.proyecto.Models.dto.response;

import grupo.proyecto.Enums.Etiquetas;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private List<Etiquetas> especialidades;
    private String email;
}
