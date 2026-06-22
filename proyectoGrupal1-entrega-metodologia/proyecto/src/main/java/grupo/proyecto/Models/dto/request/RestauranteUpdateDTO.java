package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestauranteUpdateDTO {

    private String nombre;

    private String direccion;

    private String email;

    private List<Etiquetas> especialidades;
}