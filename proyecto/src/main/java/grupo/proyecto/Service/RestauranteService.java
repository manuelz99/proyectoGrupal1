package grupo.proyecto.Service;

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
    //private final RestauranteMapper mapper;

    public RestauranteResponseDTO crearRestaurante(RestauranteRequestDTO dto) {

        //Restaurante restaurante = mapper.toEntity(dto);
        //Restaurante saved = repository.save(restaurante);

        return /*mapper.toDTO(saved)*/ new RestauranteResponseDTO();
    }

    public RestauranteResponseDTO buscarPorId(Long id) {

        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Restaurante no encontrado con ID: " + id));

        return /*mapper.toDTO(restaurante)*/ new RestauranteResponseDTO();
    }

    /*
    public List<RestauranteResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
     */
}
