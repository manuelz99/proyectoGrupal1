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
    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false)
    private String nombre;
    @NotBlank(message = "El email no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank(message = "La direccion no puede ser nula")
    @Column(nullable = false)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etiquetas preferencias;


    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<ReseñaPlato> reseñasPlato;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<ReseñaRestaurant> reseñasRestaurantes;

}
