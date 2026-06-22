package grupo.proyecto.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "promociones")
@Data
@NoArgsConstructor
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Plato> platosEnPromocion = new ArrayList<>();

    @NotNull(message = "El porcentaje a descontar es necesario para la operacion")
    @Column(nullable = false)
    private Double porcentajeDescuento; // Ej: 0.20 para un 20%
    @Column(nullable = false)
    private BigDecimal precioFinal=BigDecimal.ZERO;
    @FutureOrPresent(message = "La fecha inicio debe ser actual o futura")
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    @Future(message = "La promo debe tener fecha de finalizacion futura")
    @Column(nullable = false)
    private LocalDateTime fechaFin;
    @NotNull(message = "Debes indicar si esta activa la promo")
    private boolean activa;

}