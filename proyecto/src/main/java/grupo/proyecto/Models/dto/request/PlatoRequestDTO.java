package grupo.proyecto.Models.dto.request;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record PlatoRequestDTO(
        @NotNull(message = "El ID del restaurante no puede ser nulo")
                @Positive(message = "El ID del restaurant debe ser positivo")
        Long idRestaurante,
        @NotBlank(message = "El nombre del plato no puede estar vacio")
        String nombre,
        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,
        @NotNull(message = "El precio es obligatorio")
        BigDecimal precio,
        @NotEmpty(message = "La lista de etiquetas no puede estr vacia")
        List<Etiquetas> etiquetas
) {
}
