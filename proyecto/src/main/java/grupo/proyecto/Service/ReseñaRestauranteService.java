package grupo.proyecto.Service;

import grupo.proyecto.Mapper.ReseñaRestauranteMapper;
import grupo.proyecto.Models.ReseñaRestaurant;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.Usuario;
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
    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;
    // =========================
    // CREAR
    // =========================
    public ReseñaRestaurtanteResponseDTO crear(ReseñaRestauranteRequestDTO dto) {

        Restaurante restaurante = restauranteService.buscarEntidadPorId(dto.getRestauranteId());

        Usuario usuario = usuarioService.encontrarPorIdinterno(dto.getUsuarioId());


        ReseñaRestaurant reseña = new ReseñaRestaurant();
        reseña.setDescripcion(dto.getDescripcion());
        reseña.setCalificacion(dto.getCalificacion());
        reseña.setRestaurante(restaurante);
        reseña.setUsuario(usuario);

        return mapper.toDTO(repository.save(reseña));
    }

    // =========================
    // LISTAR TODAS
    // =========================
    public List<ReseñaRestaurtanteResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public ReseñaRestaurtanteResponseDTO buscarPorId(Long id) {

        return mapper.toDTO(devuelveReseña(id));
    }

    // =========================
    // ELIMINAR
    // =========================
    public void eliminar(Long id) {

        repository.delete(devuelveReseña(id));
    }

    // =========================
    // LISTAR POR RESTAURANTE
    // =========================
    public List<ReseñaRestaurtanteResponseDTO> listarPorRestaurante(Long idRestaurante) {

        List<ReseñaRestaurant> lista =
                repository.findByRestauranteId(idRestaurante);

        return lista.stream()
                .map(mapper::toDTO)
                .toList();
    }

    // =========================
    // PRIVATE
    // =========================
    private ReseñaRestaurant devuelveReseña(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException("Reseña no encontrada con ID: " + id));
    }
}