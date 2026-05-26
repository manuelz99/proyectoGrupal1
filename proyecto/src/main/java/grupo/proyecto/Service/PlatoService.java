package grupo.proyecto.Service;

import grupo.proyecto.Mapper.PlatoMapper;
import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.ReseñaPlato;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Repositorys.PlatoRepository;
import grupo.proyecto.Repositorys.RestauranteRepository;
import grupo.proyecto.exception.IdDuplicadoExc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class PlatoService {
    private final PlatoRepository platoRepository;
    private final RestauranteRepository restauranteRepository;
    private final PlatoMapper platoMapper;

    public PlatoResponseDTO crearPlato(PlatoRequestDTO platoRequestDTO) {
        validaIdResto(platoRequestDTO.idRestaurante());
        Plato plato = platoMapper.toEntity(platoRequestDTO);
        plato.setDisponible(true);
        plato.setRestaurante(restauranteRepository.getReferenceById(platoRequestDTO.idRestaurante()));
        plato.setReseñas(new ArrayList<ReseñaPlato>());
        platoRepository.save(plato);
        return platoMapper.toDTO(plato);
    }

    private void validaIdResto(Long id) {
        if (restauranteRepository.existsById(id)) {
            throw new IdDuplicadoExc("El id" + id + "ya se encuentra en el sistema");
        }
    }
}
