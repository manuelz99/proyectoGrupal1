package grupo.proyecto.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "reseñas_platos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReseñaPlato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato_id",nullable = false)
    @JsonIgnore
    private Plato plato;

    @NotBlank(message = "La descripcion no puede ser vacia.")
    @Column(nullable = false)
    private String descripcion;

    @Min(0)
    @Max(10)
    @NotNull(message = "La calificacion no puede ser nula")
    @Column(nullable = false)
    private Integer calificacion;
}
