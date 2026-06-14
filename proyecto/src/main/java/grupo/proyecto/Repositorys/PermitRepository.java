package grupo.proyecto.Repositorys;

import grupo.proyecto.Enums.Permits;
import grupo.proyecto.Models.PermitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermitRepository extends JpaRepository<PermitEntity, Long> {

    Optional<PermitEntity> findByPermit(Permits permit);
}
