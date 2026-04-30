package grupo.proyecto.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String direccion;
    @ElementCollection
    @CollectionTable(name = "usuario_preferencias", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "preferencia")
    private List<String> preferenciasGustos;

}
