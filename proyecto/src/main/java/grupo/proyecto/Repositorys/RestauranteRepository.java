package grupo.proyecto.Repositorys;

import grupo.proyecto.Models.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante,Long> {

    List<Restaurante> findByNombre(String nombre);
    List<Restaurante> findByDireccion(String direccion);
}
