package grupo.proyecto.Repositorys;

import grupo.proyecto.Models.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurante,Long> {
}
