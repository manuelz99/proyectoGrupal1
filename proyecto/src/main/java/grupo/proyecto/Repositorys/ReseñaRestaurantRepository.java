package grupo.proyecto.Repositorys;

import grupo.proyecto.Models.ReseñaRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReseñaRestaurantRepository extends JpaRepository<ReseñaRestaurant,Long> {
    List<ReseñaRestaurant> findByRestauranteId(Long idRestaurante);
}
