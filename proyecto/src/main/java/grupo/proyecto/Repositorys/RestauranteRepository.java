package grupo.proyecto.Repositorys;

import grupo.proyecto.Models.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante,Long> {

    List<Restaurante> findByNombreContainingIgnoreCase(String nombre);

    List<Restaurante> findByDireccionContainingIgnoreCase(String direccion);
    Optional<Restaurante> findByNombre(String nombre);
    Optional<Restaurante> findByEmail(String email);
}
