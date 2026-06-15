package grupo.proyecto.Service;

import grupo.proyecto.Mapper.RestauranteMapper;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.dto.request.RestauranteCercanoRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteUpdateDTO;
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
    private final GeocodingService geocodingService;

    public RestauranteResponseDTO crearRestaurante(RestauranteRequestDTO dto) {

        Restaurante restaurante = mapper.toEntity(dto);


        Double[] coordenadas =
                geocodingService.obtenerCoordenadas(dto.getDireccion());

        restaurante.setLatitud(coordenadas[0]);
        restaurante.setLongitud(coordenadas[1]);

        Restaurante saved = repository.save(restaurante);

        return mapper.toDTO(saved);
    }

    public RestauranteResponseDTO buscarPorId(Long id) {

        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado con ID: " + id));

        return mapper.toDTO(restaurante);
    }
    public Restaurante buscarPorIdnoDto(Long id) {

        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado con ID: " + id));

        return restaurante;
    }

    public List<RestauranteResponseDTO> listar(String nombre,
                                               String direccion) {

        List<Restaurante> restaurantes;

        if (nombre != null) {

            restaurantes =
                    repository.findByNombreContainingIgnoreCase(nombre);

        } else if (direccion != null) {

            restaurantes =
                    repository.findByDireccionContainingIgnoreCase(direccion);

        } else {

            restaurantes = repository.findAll();
        }

        return restaurantes.stream()
                .map(mapper::toDTO)
                .toList();
    }
    public RestauranteResponseDTO buscarEntidadPorNombre(String nombre) {


       Restaurante restaurante = repository.findByNombre(nombre).orElseThrow(() ->
                        new RecursoNotFoundException("Restaurante no encontrado"));

        return mapper.toDTO(restaurante);
    }
    public RestauranteResponseDTO buscarPorEmail(String email) {

        Restaurante restaurante = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado"
                        ));

        return mapper.toDTO(restaurante);
    }
    public RestauranteResponseDTO modificarRestaurante(
            Long id,
            RestauranteUpdateDTO dto
    ) {

        Restaurante restaurante =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Restaurante no encontrado"
                                ));

        restaurante.setNombre(dto.getNombre());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setEmail(dto.getEmail());
        restaurante.setEspecialidades(dto.getEspecialidades());

        Double[] coordenadas =
                geocodingService.obtenerCoordenadas(
                        dto.getDireccion()
                );

        restaurante.setLatitud(
                coordenadas[0]
        );

        restaurante.setLongitud(
                coordenadas[1]
        );

        Restaurante actualizado =
                repository.save(restaurante);

        return mapper.toDTO(actualizado);
    }
    private double distanciaMetros(
            double lat1,
            double lon1,
            double lat2,
            double lon2
    ) {

        final int R = 6371000;

        double latDistance =
                Math.toRadians(lat2 - lat1);

        double lonDistance =
                Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(latDistance / 2)
                        * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2)
                        * Math.sin(lonDistance / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return R * c;
    }
    public List<RestauranteResponseDTO> buscarCercanos(
            RestauranteCercanoRequestDTO dto
    ) {

        return repository.findAll()
                .stream()

                .filter(r -> {

                    double distancia =
                            distanciaMetros(
                                    dto.getLatitud(),
                                    dto.getLongitud(),
                                    r.getLatitud(),
                                    r.getLongitud()
                            );

                    return distancia <= dto.getRadioMetros();
                })

                .filter(r ->

                        dto.getEspecialidad() == null ||

                                r.getEspecialidades()
                                        .contains(
                                                dto.getEspecialidad()
                                        )
                )

                .map(mapper::toDTO)

                .toList();
    }
    public RestauranteResponseDTO login(String email, String password) {

        Restaurante r = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Restaurante no existe"));

        if (!r.getPassword().equals(password)) {
            throw new RuntimeException("Password incorrecta");
        }

        return mapper.toDTO(r);
    }

}
