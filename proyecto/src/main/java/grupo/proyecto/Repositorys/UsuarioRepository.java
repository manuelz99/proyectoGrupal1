package grupo.proyecto.Repositorys;

import grupo.proyecto.Enums.Etiquetas;
import grupo.proyecto.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
