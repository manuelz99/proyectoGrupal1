package grupo.proyecto.Service;

import grupo.proyecto.Enums.Roles;
import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.RoleEntity;
import grupo.proyecto.Models.dto.request.RegisterRequestDTO;
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

    public AuthService(CredentialsRepository credentialsRepository,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.credentialsRepository = credentialsRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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
    public AuthResponseDTO register(RegisterRequestDTO input) {
        if (credentialsRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        CredentialsEntity credentials = new CredentialsEntity(
                input.getEmail(),
                passwordEncoder.encode(input.getPassword())
        );

        // Asignamos rol USER por defecto
        RoleEntity userRole = roleRepository.findByRole(Roles.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException(
                        "Rol ROLE_USER no encontrado. Ejecutá el DataInitializer."));
        credentials.addRole(userRole);

        credentialsRepository.save(credentials);

        String accessToken = jwtService.generateToken(credentials);
        return new AuthResponseDTO(accessToken);
    }

}
