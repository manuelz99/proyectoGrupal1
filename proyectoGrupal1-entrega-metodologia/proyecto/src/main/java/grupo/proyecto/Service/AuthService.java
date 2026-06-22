package grupo.proyecto.Service;

import grupo.proyecto.Enums.Roles;
import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.RoleEntity;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.request.RegisterRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.AuthResponseDTO;
import grupo.proyecto.Repositorys.CredentialsRepository;
import grupo.proyecto.Repositorys.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final CredentialsRepository credentialsRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;

    public AuthService(CredentialsRepository credentialsRepository,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       RestauranteService restauranteService,
                       UsuarioService usuarioService) {
        this.credentialsRepository = credentialsRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.restauranteService = restauranteService;
        this.usuarioService = usuarioService;
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    public AuthResponseDTO authenticate(String email, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CredentialsEntity user = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String accessToken = jwtService.generateToken(user);

        return new AuthResponseDTO(accessToken);
    }

    // ── Registro ──────────────────────────────────────────────────────────────

    @Transactional
    public AuthResponseDTO registerUser(CrearUsuarioRequestDTO input) {
        if (credentialsRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario saved = usuarioService.crearUsuario(input);

        //Crear las credenciales para Spring Security
        CredentialsEntity credentials = new CredentialsEntity();
        credentials.setEmail(input.getEmail());
        credentials.setPassword(passwordEncoder.encode(input.getPassword()));
        credentials.setUsuario(saved);

        //Asignar el rol
        RoleEntity roleUser = roleRepository.findByRole(Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER no existe"));

        credentials.addRole(roleUser);
        credentialsRepository.save(credentials);

        String accessToken = jwtService.generateToken(credentials);
        return new AuthResponseDTO(accessToken);
    }

    @Transactional
    public AuthResponseDTO registerRestaurante(RestauranteRequestDTO input) {
        if (credentialsRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Restaurante saved = restauranteService.crearRestaurante(input);

        //Crear las credenciales para Spring Security
        CredentialsEntity credentials = new CredentialsEntity();
        credentials.setEmail(input.getEmail());
        credentials.setPassword(passwordEncoder.encode(input.getPassword()));
        credentials.setRestaurante(saved);

        //Asignar el rol
        RoleEntity roleResto = roleRepository.findByRole(Roles.ROLE_RESTAURANTE)
                .orElseThrow(() -> new RuntimeException("ROLE_RESTAURANTE no existe"));

        credentials.addRole(roleResto);
        credentialsRepository.save(credentials);

        String accessToken = jwtService.generateToken(credentials);
        return new AuthResponseDTO(accessToken);
    }

}
