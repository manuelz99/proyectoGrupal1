package grupo.proyecto.Service;

import grupo.proyecto.Mapper.ReseñaPlatoMapper;
import grupo.proyecto.Models.ReseñaPlato;
import grupo.proyecto.Models.dto.request.ReseñaPlatoRequestDTO;
import grupo.proyecto.Models.dto.response.ReseñaPlatoResponseDTO;
import grupo.proyecto.Repositorys.ReseñaPlatoRespository;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReseñaPlatoService {

    private final ReseñaPlatoRespository respository;
    private final ReseñaPlatoMapper mapper;

    @Transactional
    public ReseñaPlatoResponseDTO crear(ReseñaPlatoRequestDTO dto) {

        ReseñaPlato reseñaPlato = mapper.toEntity(dto);
        ReseñaPlato saved = respository.save(reseñaPlato);

        return mapper.toDTO(saved);
    }

    public List<ReseñaPlatoResponseDTO> listar() {

        return respository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ReseñaPlatoResponseDTO buscarPorId(Long id) {

        ReseñaPlato reseñaPlato = respository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Reseña no ecnontrada con ID: " + id));

        return mapper.toDTO(reseñaPlato);
    }

    public void delete(Long id) {
        ReseñaPlato reseñaPlato = respository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + id));
        respository.delete(reseñaPlato);
    }


}
