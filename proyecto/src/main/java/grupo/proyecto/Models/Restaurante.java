package grupo.proyecto.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurante")
    private Long idRestaurante;
    
    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 3, message = "La especialidad debe tener por lo menos 3 caracteres")
    private String especialidad;

    @Column(nullable = false)
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Plato> menu;

    @Column(nullable = false)
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReseñaRestaurant> reseñas;

    @NotNull(message = "Debes indicar si tiene opciones saludables o no")
    @Column(nullable = false)
    private boolean ofreceOpcionesSaludables;
}
