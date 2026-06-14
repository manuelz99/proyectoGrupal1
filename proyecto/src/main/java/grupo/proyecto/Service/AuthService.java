package grupo.proyecto.Service;

import grupo.proyecto.Enums.Roles;
import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.RoleEntity;
import grupo.proyecto.Models.dto.AuthDTO;
import grupo.proyecto.Models.dto.AuthDTO.*;
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

    @Transactional
    public AuthDTO.AuthResponse authenticate(AuthDTO.AuthRequest input) {
        // Spring Security valida las credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.username(), input.password())
        );

        CredentialsEntity user = credentialsRepository.findByEmail(input.username())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String accessToken  = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Guardamos el refresh token en la base de datos
        user.setRefreshToken(refreshToken);
        credentialsRepository.save(user);

        return new AuthDTO.AuthResponse(accessToken, refreshToken);
    }

    // ── Registro ──────────────────────────────────────────────────────────────

    @Transactional
    public AuthResponse register(RegisterRequest input) {
        if (credentialsRepository.existsByEmail(input.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        CredentialsEntity credentials = new CredentialsEntity(
                input.email(),
                passwordEncoder.encode(input.password())
        );

        // Asignamos rol USER por defecto
        RoleEntity userRole = roleRepository.findByRole(Roles.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException(
                        "Rol ROLE_USER no encontrado. Ejecutá el DataInitializer."));
        credentials.addRole(userRole);

        String refreshToken = jwtService.generateRefreshToken(credentials);
        credentials.setRefreshToken(refreshToken);

        credentialsRepository.save(credentials);

        String accessToken = jwtService.generateToken(credentials);
        return new AuthResponse(accessToken, refreshToken);
    }

    // ── Refresh Token ─────────────────────────────────────────────────────────

    @Transactional
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        CredentialsEntity user = credentialsRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Verificamos que el refresh token coincida con el almacenado
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh token no coincide");
        }

        if (!jwtService.validateRefreshToken(refreshToken, user)) {
            throw new IllegalArgumentException("Refresh token expirado o inválido");
        }

        String newAccessToken  = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        credentialsRepository.save(user);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
