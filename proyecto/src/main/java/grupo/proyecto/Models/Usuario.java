package grupo.proyecto.Models;

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
    @ElementCollection
    @CollectionTable(name = "usuario_preferencias", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "preferencia")
    private List<String> preferenciasGustos;


    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<ReseñaPlato> reseñasPlato;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<ReseñaRestaurant> reseñasRestaurantes;

}
