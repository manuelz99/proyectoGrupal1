package grupo.proyecto.Models.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromocionRequestDTO {
    @NotNull(message = "El porcentaje es obligatorio")
    @Positive(message = "El porcentaje debe ser positivo")
    @Max(100)
    private Double porcentajeDescuento;
}
