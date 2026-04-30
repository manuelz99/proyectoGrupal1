package grupo.proyecto.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Carta {
    private Long id;
    private Usuario usuario;
    private Restaurante restaurant;
    private List<ItemPedido> items;
    private Double total;
    private String estado; // Ej: "SELECCIONANDO", "CONFIRMADO", "EN_CAMINO"

    public void calcularTotal() {
        this.total = items.stream()
                .mapToDouble(item -> item.getPlato().getPrecio() * item.getCantidad())
                .sum();
    }
    @Getter
    @Setter

    class ItemPedido {
        private Plato plato;
        private Integer cantidad;
    }
}

