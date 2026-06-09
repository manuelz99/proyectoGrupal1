package grupo.proyecto.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grupo.proyecto.Enums.Etiquetas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @CollectionTable(name = "usuario_preferencias", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "etiqueta", nullable = false)
    private Set<Etiquetas> preferencias;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<ReseñaPlato> reseñasPlato;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<ReseñaRestaurant> reseñasRestaurantes;

    @ManyToMany
    @JoinTable(
            name = "usuario_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurante_id")
    )
    private Set<Restaurante> favoritos = new HashSet<>();
}
