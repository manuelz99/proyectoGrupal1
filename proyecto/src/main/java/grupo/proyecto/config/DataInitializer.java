package grupo.proyecto.config;

import grupo.proyecto.Enums.Permits;
import grupo.proyecto.Enums.Roles;
import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.PermitEntity;
import grupo.proyecto.Models.RoleEntity;
import grupo.proyecto.Repositorys.CredentialsRepository;
import grupo.proyecto.Repositorys.PermitRepository;
import grupo.proyecto.Repositorys.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PermitRepository permitRepository;
    private final RoleRepository roleRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PermitRepository permitRepository,
                           RoleRepository roleRepository,
                           CredentialsRepository credentialsRepository,
                           PasswordEncoder passwordEncoder) {
        this.permitRepository = permitRepository;
        this.roleRepository = roleRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initPermits();
        initRoles();
        initAdminUser();
    }

    // ── Permisos ──────────────────────────────────────────────────────────────

    private void initPermits() {
        Arrays.stream(Permits.values()).forEach(permit -> {
            if (permitRepository.findByPermit(permit).isEmpty()) {
                permitRepository.save(new PermitEntity(permit));
            }
        });
    }

    // ── Roles con sus permisos ────────────────────────────────────────────────

    private void initRoles() {
        // Permisos para ROLE_USER
        List<Permits> userPermits = List.of(
                Permits.VER_RESTAURANTES,
                Permits.VER_RESENAS,
                Permits.CREAR_RESENA,
                Permits.ACTUALIZAR_RESENA
        );

        // Permisos para ROLE_ADMIN (todos)
        List<Permits> adminPermits = Arrays.asList(Permits.values());

        createRoleIfNotExists(Roles.ROLE_USER, userPermits);
        createRoleIfNotExists(Roles.ROLE_ADMIN, adminPermits);
    }

    private void createRoleIfNotExists(Roles roleEnum, List<Permits> permitList) {
        if (roleRepository.findByRole(roleEnum).isEmpty()) {
            RoleEntity role = new RoleEntity(roleEnum);
            permitList.forEach(p ->
                    permitRepository.findByPermit(p).ifPresent(role::addPermit)
            );
            roleRepository.save(role);
        }
    }

    // ── Usuario admin por defecto ─────────────────────────────────────────────

    private void initAdminUser() {
        String adminEmail = "admin@gmail.com";
        if (!credentialsRepository.existsByEmail(adminEmail)) {
            CredentialsEntity admin = new CredentialsEntity(
                    adminEmail,
                    passwordEncoder.encode("admin1234")
            );
            roleRepository.findByRole(Roles.ROLE_ADMIN).ifPresent(admin::addRole);
            credentialsRepository.save(admin);
            System.out.println("Usuario admin creado: " + adminEmail + " / admin1234");
        }
    }
}
