package grupo.proyecto.Models;

import lombok.Data;

import java.util.List;
@Data
public class Plato {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private List<String> etiquetas; // Ej: "Keto", "Sin Azúcar", "Proteico"
    private boolean disponible;

    // Constructores, Getters y Setters
}
