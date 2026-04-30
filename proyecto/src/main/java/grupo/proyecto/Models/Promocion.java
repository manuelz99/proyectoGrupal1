package grupo.proyecto.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promociones")
@Data
@NoArgsConstructor
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @Column(nullable = false)
    private Double porcentajeDescuento; // Ej: 0.20 para un 20%

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToMany
    @JoinTable(
            name = "promocion_platos",
            joinColumns = @JoinColumn(name = "promocion_id"),
            inverseJoinColumns = @JoinColumn(name = "plato_id")
    )
    private List<Plato> platosAsociados;

    private boolean activa = true;
}