package grupo.proyecto.Service;

import grupo.proyecto.Enums.Roles;
import grupo.proyecto.Mapper.RestauranteMapper;
import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.RoleEntity;
import grupo.proyecto.Models.dto.request.RestauranteCercanoRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteUpdateDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Repositorys.CredentialsRepository;
import grupo.proyecto.Repositorys.RestauranteRepository;
import grupo.proyecto.Repositorys.RoleRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository repository;
    private final RestauranteMapper mapper;
    private final GeocodingService geocodingService;
    private final CredentialsRepository credentialsRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // =========================
    // CREAR
    // =========================
    @Transactional
    public RestauranteResponseDTO crearRestaurante(RestauranteRequestDTO dto) {

        Restaurante restaurante = mapper.toEntity(dto);

        Double[] coordenadas = geocodingService.obtenerCoordenadas(dto.getDireccion());
        restaurante.setLatitud(coordenadas[0]);
        restaurante.setLongitud(coordenadas[1]);

        restaurante.setPassword(passwordEncoder.encode(dto.getPassword()));

        Restaurante saved = repository.save(restaurante);

        // 2. Crear las credenciales para Spring Security
        CredentialsEntity credentials = new CredentialsEntity();
        credentials.setEmail(dto.getEmail());
        credentials.setPassword(restaurante.getPassword());
        credentials.setRestaurante(saved);

        // 3. Asignar el rol
        RoleEntity roleResto = roleRepository.findByRole(Roles.ROLE_RESTAURANTE)
                .orElseThrow(() -> new RuntimeException("ROLE_RESTAURANTE no existe"));

        credentials.addRole(roleResto);
        credentialsRepository.save(credentials);

        return mapper.toDTO(saved);
    }

    // =========================
    // BUSCAR POR ID (DTO)
    // =========================
    public RestauranteResponseDTO buscarPorId(Long id) {

        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado con ID: " + id));

        return mapper.toDTO(restaurante);
    }

    // =========================
    // BUSCAR POR ID (ENTITY)
    // =========================
    public Restaurante buscarEntidadPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado con ID: " + id));
    }

    // =========================
    // LISTAR CON FILTROS
    // =========================
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

    // =========================
    // BUSCAR POR NOMBRE
    // =========================
    public RestauranteResponseDTO buscarPorNombre(String nombre) {

        Restaurante restaurante = repository.findByNombre(nombre)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado"
                        ));

        return mapper.toDTO(restaurante);
    }

    // =========================
    // BUSCAR POR EMAIL
    // =========================
    public RestauranteResponseDTO buscarPorEmail(String email) {

        Restaurante restaurante = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado"
                        ));

        return mapper.toDTO(restaurante);
    }

    // =========================
    // MODIFICAR
    // =========================
    @Transactional
    public RestauranteResponseDTO modificarRestaurante(
            Long id,
            RestauranteUpdateDTO dto
    ) {

        Restaurante restaurante =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RecursoNotFoundException(
                                        "Restaurante no encontrado"
                                ));

        restaurante.setNombre(dto.getNombre());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setEmail(dto.getEmail());
        restaurante.setEspecialidades(dto.getEspecialidades());

        Double[] coordenadas =
                geocodingService.obtenerCoordenadas(dto.getDireccion());

        restaurante.setLatitud(coordenadas[0]);
        restaurante.setLongitud(coordenadas[1]);

        return mapper.toDTO(repository.save(restaurante));
    }

    // =========================
    // DISTANCIA (HUBO OK)
    // =========================
    private double distanciaMetros(
            double lat1,
            double lon1,
            double lat2,
            double lon2
    ) {

        final int R = 6371000;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2)
                        * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // =========================
    // RESTAURANTES CERCANOS
    // =========================
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
                                r.getEspecialidades().contains(dto.getEspecialidad())
                )

                .map(mapper::toDTO)
                .toList();
    }
}