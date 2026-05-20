package grupo.proyecto.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reseñas")
@Getter
@Setter
public class ReseñaRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReseña;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurante restaurante;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id",nullable = false)
    private Usuario usuario;
    @Column(nullable = false)
    private String descripcion;

    @Min(0)
    @Max(10)
    @NotNull(message = "La calificacion no puede estar vacia")
    @Column(nullable = false)
    private int calificacion;



}
