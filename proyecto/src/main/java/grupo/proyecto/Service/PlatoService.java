package grupo.proyecto.Service;

import grupo.proyecto.Mapper.PlatoMapper;
import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.ReseñaPlato;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Repositorys.PlatoRepository;
import grupo.proyecto.Repositorys.RestauranteRepository;
import grupo.proyecto.exception.IdDuplicadoExc;
import grupo.proyecto.exception.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    public void eliminar_plato(Long id) {
        if (!restauranteRepository.existsById(id)) {
            throw new RecursoNotFoundException("No existe ese plato jej");
        }
        restauranteRepository.delete(restauranteRepository.getReferenceById(id));
    }

    public List<Plato> listarPlatosPorRestauranteYDisponibilidad(Long idRestaurante) {
        validaIdResto(idRestaurante);
        return platoRepository.findByRestauranteIdAndEstado(idRestaurante, true);
    }

    ;

    //SIN RESPONSE, PARA ADMINISTRADOR
    public List<Plato> listaTodosLosPlatosDeUnResto(Long idRestaurante) {

        validaIdResto(idRestaurante);
        return platoRepository.findByRestauranteId(idRestaurante);
    }

    public String cambiaEstadoDePlato(Long idPlato) {
        Plato plato = devuelvePlato(idPlato);
        if (plato.isDisponible()) {
            plato.setDisponible(false);
            return "Estado cambiado a no disponible.";
        } else plato.setDisponible(true);
        return "Estado cambiado a verdadero.";
    }

    public List<Plato> listaTodosLosPlatos(Long idRestaurante) {
        return platoRepository.findAll().stream().toList();
    }

    public PlatoResponseDTO hacerDTO(Long id) {
        Plato plato = devuelvePlato(id);
        return platoMapper.toDTO(plato);
    }

    //METODOS PRIVADOS DE BUSQUEDA
    private Plato devuelvePlato(Long id) {
        return platoRepository.findById(id).orElseThrow(() -> new RecursoNotFoundException("Plato no encontrado por ID"));
    }

    private void validaIdResto(Long id) {
        if (restauranteRepository.existsById(id)) {
            throw new IdDuplicadoExc("El id" + id + "ya se encuentra en el sistema");
        }
    }
}
