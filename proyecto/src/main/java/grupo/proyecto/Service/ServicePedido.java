package grupo.proyecto.Service;

import grupo.proyecto.Models.Pedido;
import grupo.proyecto.Repositorys.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ServicePedido {
    @Autowired
    PedidoRepository pedidoRepository;
}
