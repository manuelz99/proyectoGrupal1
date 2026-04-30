package grupo.proyecto.Models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class Usuario {
    @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String nombre;
        private String email;
        private String direccion;
        private List<String> preferenciasGustos; // Ej: "Vegano", "Sin Azúcar", "Celíaco"

}
