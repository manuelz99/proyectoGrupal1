package grupo.proyecto.Models;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
    private String contrasenia;

    @Column(nullable = false)
    private String direccion;

    @ElementCollection(targetClass = Etiquetas.class)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<Etiquetas> preferencias;

    @OneToMany(mappedBy = "usuario")
    private List<ReseñaPlato> reseñasPlato;

    @OneToMany(mappedBy = "usuario")
    private List<ReseñaRestaurant> reseñasRestaurantes;

}
