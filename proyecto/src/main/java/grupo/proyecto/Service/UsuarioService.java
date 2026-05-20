package grupo.proyecto.Service;

import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Repositorys.UsuarioRepository;
import grupo.proyecto.exception.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    //private final UsuarioMapper usuarioMapper:

    public UsuarioResponseDTO crearUsuario(CrearUsuarioRequestDTO requestDTO){
        Usuario usuario = /*usuarioMapper.toEntity(requestDTO)*/null;

        Usuario guardado = usuarioRepository.save(usuario);

        return /* usuarioMapper.toDTO(guardado) */new UsuarioResponseDTO();
    }

    /*
    public List<UsuarioResponseDTO> listar(){
        return  usuarioRepository.findAll()
                .stream()
                .map(a -> usuarioMapper.toDTO(a))
                .toList();
    }
    */

    public UsuarioResponseDTO encontrarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("El usuario que busca no existe"));

        return /* usuarioMapper.toDTO(usuario) */new UsuarioResponseDTO();
    }
}
