package grupo.proyecto.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
@Entity
@Table(name = "restaurantes")
@NoArgsConstructor
@Data
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String especialidad;
    @Column(nullable = false)// Ej: "Sushi", "Parrilla"
    private Double calificacion;
    @OneToMany(mappedBy = "restaurante",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Plato> menu;

    private boolean ofreceOpcionesSaludables;

    // Constructores, Getters y Setters
}
