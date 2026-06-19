package grupo.proyecto.Service;

import grupo.proyecto.Mapper.PlatoMapper;
import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.ReseñaPlato;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.PlatoUpdateDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Repositorys.PlatoRepository;
import grupo.proyecto.Repositorys.RestauranteRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository platoRepository;
    private final RestauranteRepository restauranteRepository;
    private final PlatoMapper platoMapper;

    // =========================
    // CREAR PLATO
    // =========================
    @Transactional
    public PlatoResponseDTO crearPlato(PlatoRequestDTO dto, Long restoId) {

        Restaurante restaurante = validarRestaurante(restoId);

        Plato plato = platoMapper.toEntity(dto);

        plato.setDisponible(true);
        plato.setRestaurante(restaurante);
        plato.setReseñas(new ArrayList<ReseñaPlato>());

        return platoMapper.toDTO(platoRepository.save(plato));
    }

    // =========================
    // ELIMINAR PLATO
    // =========================
    @Transactional
    public void eliminar_plato(Long restoId, Long platoId) {

        Plato plato = validarPlato(platoId);

        if (!plato.getRestaurante().getId().equals(restoId)) {
            throw new AccessDeniedException("El plato no pertenece a este restaurante");
        }

        platoRepository.delete(plato);
    }

    // =========================
    // MODIFICAR PLATO
    // =========================
    @Transactional
    public PlatoResponseDTO modificarPlato(Long restoId, Long platoId, PlatoUpdateDTO dto) {

        Plato plato = validarPlato(platoId);

        if (!plato.getRestaurante().getId().equals(restoId)) {
            throw new AccessDeniedException("El plato no pertenece a este restaurante");
        }

        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setPrecio(dto.getPrecio());
        plato.setDisponible(dto.getDisponible());

        return platoMapper.toDTO(platoRepository.save(plato));
    }

    // =========================
    // LISTAR POR RESTAURANTE
    // =========================
    public List<PlatoResponseDTO> listarPorRestaurante(Long idRestaurante) {

        validarRestaurante(idRestaurante);

        List<Plato> platos = platoRepository.findByRestauranteId(idRestaurante);

        List<PlatoResponseDTO> dtos = new ArrayList<>();

        for (Plato p : platos) {
            try {
                dtos.add(platoMapper.toDTO(p));
            } catch (Exception e) {
                System.out.println("ERROR EN PLATO ID: " + p.getId());
                e.printStackTrace();
            }
        }

        return dtos;
    }

    // =========================
    // LISTAR DISPONIBLES
    // =========================
    public List<PlatoResponseDTO> listarDisponibles(Long idRestaurante) {

        validarRestaurante(idRestaurante);

        return platoRepository
                .findByRestauranteIdAndDisponible(idRestaurante, true)
                .stream()
                .map(platoMapper::toDTO)
                .toList();
    }

    // =========================
    // LISTAR TODOS
    // =========================
    public List<PlatoResponseDTO> listaTodosLosPlatos(Long idRestaurante) {

        if (!restauranteRepository.existsById(idRestaurante)) {
            throw new RecursoNotFoundException("Restaurante no encontrado");
        }

        return platoRepository.findByRestauranteId(idRestaurante)
                .stream()
                .map(platoMapper::toDTO)
                .toList();
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public PlatoResponseDTO hacerDTO(Long id) {

        return platoMapper.toDTO(validarPlato(id));
    }

    // =========================
    // CAMBIAR ESTADO (DISPONIBLE / NO DISPONIBLE)
    // =========================
    @Transactional
    public String cambiaEstadoDePlato(Long idPlato) {

        Plato plato = validarPlato(idPlato);

        plato.setDisponible(!plato.isDisponible());
        platoRepository.save(plato);

        return plato.isDisponible()
                ? "Estado cambiado a disponible"
                : "Estado cambiado a no disponible";
    }

    // =========================
    // VALIDACIONES PRIVADAS
    // =========================
    private Restaurante validarRestaurante(Long id) {

        return restauranteRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException("Restaurante no encontrado con ID: " + id));
    }

    private Plato validarPlato(Long id) {

        return platoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException("Plato no encontrado con ID: " + id));
    }

    public List<PlatoResponseDTO> listarPlatosPorNombreDeRestaurante(String resto){
        Restaurante restaurante = restauranteRepository.findByNombre(resto)
                .orElseThrow(() ->
                      new RecursoNotFoundException("Restaurante no encontrado"));

        return listarPorRestaurante(restaurante.getId());
    }
}