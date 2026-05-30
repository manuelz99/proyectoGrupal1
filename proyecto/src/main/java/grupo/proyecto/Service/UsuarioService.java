package grupo.proyecto.Service;

import grupo.proyecto.Mapper.UsuarioMapper;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Repositorys.UsuarioRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional
    public UsuarioResponseDTO crearUsuario(CrearUsuarioRequestDTO requestDTO){
        Usuario usuario = usuarioMapper.toEntity(requestDTO);

        Usuario guardado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(guardado);
    }

    public List<UsuarioResponseDTO> listar(){
        return  usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }


    public UsuarioResponseDTO encontrarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("El usuario que busca no existe"));

        return usuarioMapper.toDTO(usuario);
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
}
