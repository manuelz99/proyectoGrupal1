package grupo.proyecto.Service;

import grupo.proyecto.Mapper.RestauranteMapper;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Repositorys.RestauranteRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository repository;
    private final RestauranteMapper mapper;

    public RestauranteResponseDTO crearRestaurante(RestauranteRequestDTO dto) {

        Restaurante restaurante = mapper.toEntity(dto);
        Restaurante saved = repository.save(restaurante);

        return mapper.toDTO(saved);
    }

    public RestauranteResponseDTO buscarPorId(Long id) {

        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Restaurante no encontrado con ID: " + id));

        return mapper.toDTO(restaurante);
    }

    public List<RestauranteResponseDTO> listar(String nombre, String direccion) {

        List<Restaurante> restaurantes;

        if (nombre != null) {

            restaurantes = repository.findByNombre(nombre);
        } else if (direccion != null){

            restaurantes = repository.findByDireccion(direccion);
        } else {

            restaurantes = repository.findAll();
        }

        return restaurantes.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
