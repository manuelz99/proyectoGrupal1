package grupo.proyecto.Models;

import grupo.proyecto.Enums.Etiquetas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La direccion del restaurant no puede ser nula.")
    @Column(nullable = false)
    private String direccion;
    
    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Coordenadas para el mapa
    @NotNull
    private Double latitud;
    @NotNull
    private Double longitud;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Plato> menus;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReseñaRestaurant> reseñas;


    @ElementCollection(targetClass = Etiquetas.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "restaurante_especialidades",
            joinColumns = @JoinColumn(name = "id_restaurante")
    )
    @Column(name = "especialidad")
    private List<Etiquetas> especialidades;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;
}
