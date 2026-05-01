package grupo.proyecto.Repositorys;

import grupo.proyecto.Models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
