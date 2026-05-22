package grupo.proyecto.Service;

import grupo.proyecto.Repositorys.PromocionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromocionService {
    private final PromocionRepository promocionRepository;
    private final RestauranteService restaurantService;

}
