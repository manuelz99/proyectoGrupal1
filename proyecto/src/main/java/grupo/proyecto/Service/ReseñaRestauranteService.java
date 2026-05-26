package grupo.proyecto.Service;

import grupo.proyecto.Mapper.ReseñaRestauranteMapper;
import grupo.proyecto.Models.ReseñaRestaurant;
import grupo.proyecto.Models.dto.request.ReseñaRestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.ReseñaRestaurtanteResponseDTO;
import grupo.proyecto.Repositorys.ReseñaRestaurantRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReseñaRestauranteService {

    private final ReseñaRestaurantRepository repository;
    private final ReseñaRestauranteMapper mapper;

    public ReseñaRestaurtanteResponseDTO crear(ReseñaRestauranteRequestDTO dto) {

        ReseñaRestaurant reseñaRestaurant = mapper.toEntity(dto);
        ReseñaRestaurant saved = repository.save(reseñaRestaurant);

        return mapper.toDTO(saved);
    }

    public List<ReseñaRestaurtanteResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ReseñaRestaurtanteResponseDTO buscarPorId(Long id) {

        ReseñaRestaurant reseñaRestaurant=devuelveReseña(id);
        return mapper.toDTO(reseñaRestaurant);
    }

    public void eliminar(Long id) {

        ReseñaRestaurant reseñaRestaurant=devuelveReseña(id);
        repository.delete(reseñaRestaurant);
    }
    //METODOS PRIVADOS DE BUSQUEDA
    private ReseñaRestaurant devuelveReseña(Long id){
        return  repository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Reseña no encontrada con ID: " + id));
    }
}
