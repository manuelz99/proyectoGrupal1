package grupo.proyecto.Repositorys;

import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato,Long> {

    List<Plato> findByRestauranteIdAndEstado(Long idResto,boolean estado);
    List<Plato> findByRestauranteId(Long idResto);
}
