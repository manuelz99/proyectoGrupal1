package grupo.proyecto.Models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReseñaRestaurtanteResponseDTO {

    private Long id;
    private String descripcion;
    private Integer calificacion;
    private Long restauranteId;
   // private Long usuarioId;
}
