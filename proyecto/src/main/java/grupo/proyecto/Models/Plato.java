package grupo.proyecto.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grupo.proyecto.Enums.Etiquetas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "platos")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del plato no puede ser nulo.")
    @Column(nullable = false)
    private String nombre;
    @NotBlank(message = "La descripcion no puede ser nula.")
    @Column(nullable = false)
    private String descripcion;
    @NotNull(message = "El precio no puede ser nulo")
    @Column(nullable = false)
    private BigDecimal precio;
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "El plato no puede estar sin etiquetas")
    private Etiquetas etiquetas;

    @Column(nullable = false)
    private boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    @JsonIgnore
    private Restaurante restaurante;
    @OneToMany(mappedBy = "plato",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ReseñaPlato> reseñas;

}
