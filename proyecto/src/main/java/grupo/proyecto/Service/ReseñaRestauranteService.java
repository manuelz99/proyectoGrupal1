package grupo.proyecto.Service;

import grupo.proyecto.Mapper.ReseñaRestauranteMapper;
import grupo.proyecto.Models.ReseñaRestaurant;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.ReseñaRestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.ReseñaRestaurtanteResponseDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
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
    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;
    public ReseñaRestaurtanteResponseDTO crear(ReseñaRestauranteRequestDTO dto) {

        Restaurante restaurante = restauranteService.buscarPorIdnoDto(dto.getRestauranteId());

        Usuario usuario = usuarioService.encontrarPorIdnoDTO(dto.getUsuarioId());

        ReseñaRestaurant reseña = new ReseñaRestaurant();
        reseña.setDescripcion(dto.getDescripcion());
        reseña.setCalificacion(dto.getCalificacion());
        reseña.setRestaurante(restaurante);
        reseña.setUsuario(usuario);

        return mapper.toDTO(repository.save(reseña));
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

    private ReseñaRestaurant devuelveReseña(Long id){
        return  repository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Reseña no encontrada con ID: " + id));
    }
    public List<ReseñaRestaurtanteResponseDTO> listarPorRestaurante(Long restauranteId) {
        return repository.findByRestauranteId(restauranteId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
