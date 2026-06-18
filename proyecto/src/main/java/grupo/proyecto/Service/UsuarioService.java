package grupo.proyecto.Service;

import grupo.proyecto.Enums.Roles;
import grupo.proyecto.Mapper.UsuarioMapper;
import grupo.proyecto.Models.CredentialsEntity;
import grupo.proyecto.Models.RoleEntity;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Repositorys.CredentialsRepository;
import grupo.proyecto.Repositorys.RoleRepository;
import grupo.proyecto.Repositorys.UsuarioRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UsuarioResponseDTO crearUsuario(CrearUsuarioRequestDTO dto) {

        // 1. Crear usuario
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setDireccion("Creada, pero no ingresada");

        Usuario guardadoUsuario = usuarioRepository.save(usuario);

        // 2. Crear credentials asociadas
        CredentialsEntity credentials = new CredentialsEntity();
        credentials.setEmail(dto.getEmail());
        credentials.setPassword(passwordEncoder.encode(dto.getContrasenia()));

        // 3. vincular usuario <-> credentials (CLAVE)
        credentials.setUsuario(guardadoUsuario);

        // 4. asignar rol USER
        RoleEntity roleUser = roleRepository.findByRole(Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER no existe"));

        credentials.addRole(roleUser);

        // 5. guardar credentials
        credentialsRepository.save(credentials);

        // 6. devolver DTO
        return usuarioMapper.toDTO(guardadoUsuario);
    }

    public UsuarioResponseDTO encontrarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario no existe"));

        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioResponseDTO encontrarPorEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario no existe"));

        return usuarioMapper.toDTO(usuario);
    }
    public Usuario encontrarPorIdinterno(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario no existe"));

        return usuario;
    }

    @Transactional
    public PreferenciasResponseDTO agregarPreferencias(ActualizarPreferenciasRequestDTO dto, Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        usuario.getPreferencias().addAll(dto.getEtiquetas());

        usuarioRepository.save(usuario);

        return usuarioMapper.toPreferenciasDTO(usuario.getPreferencias());
    }

    @Transactional
    public PreferenciasResponseDTO eliminarPreferencias(ActualizarPreferenciasRequestDTO dto, Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        usuario.getPreferencias().removeAll(dto.getEtiquetas());

        usuarioRepository.save(usuario);

        return usuarioMapper.toPreferenciasDTO(usuario.getPreferencias());
    }

    public PreferenciasResponseDTO listarPreferencias(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        return usuarioMapper.toPreferenciasDTO(usuario.getPreferencias());
    }

    @Transactional
    public UsuarioResponseDTO actualizarPerfil(CrearUsuarioRequestDTO requestDTO, Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        usuarioMapper.updateUsuarioFromDto(requestDTO, usuario);//con la implementacion de security esto seguramente cambie

        return usuarioMapper.toDTO(usuario);
    }
}