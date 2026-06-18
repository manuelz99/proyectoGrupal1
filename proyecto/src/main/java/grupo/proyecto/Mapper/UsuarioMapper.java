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

    // Usamos un método default para enseñarle a MapStruct cómo envolver la lista
    default PreferenciasResponseDTO toPreferenciasDTO(List<Etiquetas> etiquetas) {
        if (etiquetas == null) {
            return null;
        }
        PreferenciasResponseDTO dto = new PreferenciasResponseDTO();
        dto.setEtiquetas(etiquetas);
        return dto;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "preferencias", ignore = true)
    @Mapping(target = "reseñasPlato", ignore = true)
    @Mapping(target = "reseñasRestaurantes", ignore = true)
    void updateUsuarioFromDto(CrearUsuarioRequestDTO dto, @MappingTarget Usuario usuario);

}