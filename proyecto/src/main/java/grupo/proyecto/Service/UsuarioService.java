package grupo.proyecto.Service;

import grupo.proyecto.Mapper.RestauranteMapper;
import grupo.proyecto.Mapper.UsuarioMapper;
import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.FavoritosResponseDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.Repositorys.UsuarioRepository;
import grupo.proyecto.exception.FavoritoYaExisteException;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RestauranteService restauranteService;
    private final UsuarioMapper usuarioMapper;
    private final RestauranteMapper restauranteMapper;

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

    @Transactional
    public UsuarioResponseDTO actualizarPerfil(CrearUsuarioRequestDTO requestDTO, Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        usuarioMapper.updateUsuarioFromDto(requestDTO, usuario);//con la implementacion de security esto seguramente cambie

        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public void agregarFavoritos(Long usuarioId, Long restoId){  //con security entiendo que hay que dejar de pasar el id por parametro
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));
        Restaurante restaurante = /*restauranteService.buscarEntidadPorId(restoId)*/new Restaurante();
        //Necesito metodo en restaurante que devuelva un restaurante en cambio de un responseDTO

        if(!usuario.getFavoritos().add(restaurante)) {
            throw new FavoritoYaExisteException("El restaurante ya existe en la lista de favoritos");
        }
        usuario.getFavoritos().add(restaurante);
    }

    @Transactional
    public void eliminarFavoritos(Long usuarioId, Long restoId){  //con security entiendo que hay que dejar de pasar el id por parametro
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));
        Restaurante restaurante = /*restauranteService.buscarEntidadPorId(restoId)*/new Restaurante();

        if(!usuario.getFavoritos().add(restaurante)) {
            throw new FavoritoYaExisteException("El restaurante ya existe en la lista de favoritos");
        }
        usuario.getFavoritos().remove(restaurante);
    }

    public FavoritosResponseDTO verFavoritos(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        FavoritosResponseDTO favoritos = new FavoritosResponseDTO(usuario.getFavoritos()
                .stream()
                .map(restauranteMapper::toDTO)
                .toList());

        return favoritos;
    }
}
