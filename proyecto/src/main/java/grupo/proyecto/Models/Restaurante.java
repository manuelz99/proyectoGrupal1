package grupo.proyecto.Models;

import lombok.Data;

import java.util.List;
@Data
public class Restaurante {
    private Long id;
    private String nombre;
    private String especialidad; // Ej: "Sushi", "Parrilla"
    private Double calificacion;
    private List<Plato> menu;
    private boolean ofreceOpcionesSaludables;

    // Constructores, Getters y Setters
}
