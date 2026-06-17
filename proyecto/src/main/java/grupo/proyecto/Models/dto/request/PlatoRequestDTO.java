package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class PlatoRequestDTO {

        @NotNull(message = "El ID del restaurante no puede ser nulo")
        @Positive(message = "El ID del restaurant debe ser positivo")
        private Long idRestaurante;

        @NotBlank(message = "El nombre del plato no puede estar vacio")
        private String nombre;

        @NotBlank(message = "La descripcion es obligatoria")
        private String descripcion;

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser positivo")
        private BigDecimal precio;

        @NotEmpty(message = "La lista de etiquetas no puede estr vacia")
        private List<Etiquetas> etiquetas;
}