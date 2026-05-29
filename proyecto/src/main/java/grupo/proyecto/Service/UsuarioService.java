package grupo.proyecto.Service;

import grupo.proyecto.Enums.Etiquetas;
import grupo.proyecto.Mapper.UsuarioMapper;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Repositorys.UsuarioRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

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

    public Set<Etiquetas> aniadirPrefencias(List<Etiquetas> preferenciasNuevas, Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.getPreferencias().addAll(preferenciasNuevas);

        usuarioRepository.save(usuario);

        return usuario.getPreferencias();//hay que modificar para que devuelva un set
    }
}
