package grupo.proyecto.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@Table(name = "platos")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Double precio;
    @ElementCollection(fetch = FetchType.EAGER) // Correcto para listas de tipos simples
    @CollectionTable(name = "plato_etiquetas", joinColumns = @JoinColumn(name = "plato_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas; // Ej: "Keto", "Sin Azúcar", "Vegano"

    private boolean disponible=true;

    // Constructores, Getters y Setters
}
