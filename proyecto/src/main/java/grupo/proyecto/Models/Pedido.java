package grupo.proyecto.Models;

import grupo.proyecto.Enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "pedidos")
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurante restaurant;
    @Column(nullable = false)
    private LocalDate fechaAlta;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemPedido> items;

    @Column(nullable = false)
    private Double total;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estadoPedido;

    public void calcularTotal() {
        this.total = items.stream()
                .mapToDouble(item -> item.getPlato().getPrecio() * item.getCantidad())
                .sum();
    }

    @Entity
    @Table(name = "pedido_items")
    @Data
    @NoArgsConstructor
    class ItemPedido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "plato_id")
        private Plato plato;

        private Integer cantidad;

        @ManyToOne
        @JoinColumn(name = "pedido_id")
        private Pedido pedido;
    }
}

