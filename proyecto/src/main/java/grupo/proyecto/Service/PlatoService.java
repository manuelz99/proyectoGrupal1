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

        validaIdResto(
                platoRequestDTO.idRestaurante()
        );

        Plato plato =
                platoMapper.toEntity(platoRequestDTO);

        Restaurante restaurante =
                restauranteRepository.getReferenceById(
                        platoRequestDTO.idRestaurante()
                );

        plato.setDisponible(true);

        plato.setRestaurante(restaurante);

        plato.setReseñas(
                new ArrayList<ReseñaPlato>()
        );

        platoRepository.save(plato);

        return platoMapper.toDTO(plato);
    }


    public void eliminar_plato(Long id) {

        if (!platoRepository.existsById(id)) {

            throw new RecursoNotFoundException(
                    "No existe plato con id " + id
            );
        }

        platoRepository.deleteById(id);
    }

    public List<PlatoResponseDTO> listarPlatosPorRestauranteYDisponibilidad(Long idRestaurante) {

        validaIdResto(idRestaurante);

        List<Plato> lista = platoRepository
                .findByRestauranteIdAndDisponible(
                        idRestaurante,
                        true
                );
        List<PlatoResponseDTO> retorno = lista.stream().map(platoMapper::toDTO).toList();
        return retorno;
    }

    public List<Plato> listaTodosLosPlatosDeUnResto(Long idRestaurante) {

        validaIdResto(idRestaurante);

        return platoRepository
                .findByRestauranteId(idRestaurante);
    }

    public String cambiaEstadoDePlato(Long idPlato) {

        Plato plato =
                devuelvePlato(idPlato);

        plato.setDisponible(
                !plato.isDisponible()
        );

        platoRepository.save(plato);

        if (plato.isDisponible()) {

            return "Estado cambiado a disponible.";
        }

        return "Estado cambiado a no disponible.";
    }


    public List<PlatoResponseDTO> listaTodosLosPlatos(Long idRestaurante) {

        validaIdResto(idRestaurante);

        List<Plato> lista = platoRepository
                .findByRestauranteIdAndDisponible(
                        idRestaurante,
                        true
                );
        List<PlatoResponseDTO> retorno = lista.stream().map(platoMapper::toDTO).toList();
        return retorno;
    }

    public PlatoResponseDTO hacerDTO(Long id) {

        Plato plato = devuelvePlato(id);

        return platoMapper.toDTO(plato);
    }

    private Plato devuelvePlato(Long id) {

        return platoRepository.findById(id)

                .orElseThrow(() ->

                        new RecursoNotFoundException(
                                "Plato no encontrado con id " + id
                        )
                );
    }

    private void validaIdResto(Long id) {

        if (!restauranteRepository.existsById(id)) {

            throw new RecursoNotFoundException(
                    "No existe restaurante con id " + id
            );
        }
    }
    public PlatoResponseDTO modificarPlato(
            Long id,
            PlatoUpdateDTO dto
    ) {

        Plato plato = platoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Plato no encontrado"
                        )
                );

        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setPrecio(dto.getPrecio());
        plato.setEtiquetas(dto.getEtiquetas());

        if (dto.getDisponible() != null) {
            plato.setDisponible(dto.getDisponible());
        }

        platoRepository.save(plato);

        return platoMapper.toDTO(plato);
    }
    public List<PlatoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return platoRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(platoMapper::toDTO)
                .toList();
    }
}