package grupo.proyecto.Mapper;

import grupo.proyecto.Enums.Etiquetas;
import grupo.proyecto.Models.Usuario;
import grupo.proyecto.Models.dto.request.ActualizarPreferenciasRequestDTO;
import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.response.PreferenciasResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "preferencias", ignore = true)
    @Mapping(target = "reseñasPlato", ignore = true)
    @Mapping(target = "reseñasRestaurantes", ignore = true)
    Usuario toEntity(CrearUsuarioRequestDTO dto);

    UsuarioResponseDTO toDTO(Usuario usuario);

    Usuario toEntity(ActualizarPreferenciasRequestDTO dto);

    PreferenciasResponseDTO toPreferenciasDTO(List<Etiquetas> etiquetas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "preferencias", ignore = true)
    @Mapping(target = "reseñasPlato", ignore = true)
    @Mapping(target = "reseñasRestaurantes", ignore = true)
    void updateUsuarioFromDto(CrearUsuarioRequestDTO dto, @MappingTarget Usuario usuario);

}