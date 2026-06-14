package grupo.proyecto.Models.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteResponseDTO {

    private Long id;
    private String direccion;
    private String nombre;
    private String especialidad;
    private Boolean ofreceOpcionesSaludables;
}
