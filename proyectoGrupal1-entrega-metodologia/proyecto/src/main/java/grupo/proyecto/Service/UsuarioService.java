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
import grupo.proyecto.Repositorys.RestauranteRepository;
import grupo.proyecto.Repositorys.UsuarioRepository;
import grupo.proyecto.exception.FavoritoYaExisteException;
import grupo.proyecto.exception.RecursoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;

    public Usuario crearUsuario(CrearUsuarioRequestDTO dto) {

        // 1. Crear usuario
        Usuario usuario = usuarioMapper.toEntity(dto);

        return usuarioRepository.save(usuario);
    }

    public UsuarioResponseDTO usuarioADto(Usuario u){
        return usuarioMapper.toDTO(u);
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
/*
    @Transactional
    public UsuarioResponseDTO actualizarPerfil(CrearUsuarioRequestDTO requestDTO, Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));

        usuarioMapper.updateUsuarioFromDto(requestDTO, usuario);//con la implementacion de security esto seguramente cambie

        return usuarioMapper.toDTO(usuario);
    }*/

    @Transactional
    public FavoritosResponseDTO agregarFavoritos(Long usuarioId, Long restoId){  //con security entiendo que hay que dejar de pasar el id por parametro
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));
        Restaurante restaurante = restauranteRepository.findById(restoId)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado"));

        if(!usuario.getFavoritos().add(restaurante)) {
            throw new FavoritoYaExisteException("El restaurante ya existe en la lista de favoritos");
        }

        return new FavoritosResponseDTO(usuario.getFavoritos()
                .stream()
                .map(restauranteMapper::toDTO)
                .toList());
    }

    @Transactional
    public FavoritosResponseDTO eliminarFavoritos(Long usuarioId, Long restoId){  //con security entiendo que hay que dejar de pasar el id por parametro
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RecursoNotFoundException("Usuario no encontrado"));
        Restaurante restaurante = restauranteRepository.findById(restoId)
                .orElseThrow(() ->
                        new RecursoNotFoundException(
                                "Restaurante no encontrado"));

        if(!usuario.getFavoritos().remove(restaurante)) {
            throw new FavoritoYaExisteException("El restaurante ya existe en la lista de favoritos");
        }


        return new FavoritosResponseDTO(usuario.getFavoritos()
                .stream()
                .map(restauranteMapper::toDTO)
                .toList());
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