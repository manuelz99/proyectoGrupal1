package grupo.proyecto.Mapper;

import grupo.proyecto.Models.ReseñaPlato;
import grupo.proyecto.Models.dto.request.ReseñaPlatoRequestDTO;
import grupo.proyecto.Models.dto.response.ReseñaPlatoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReseñaPlatoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "plato", ignore = true)
    ReseñaPlato toEntity(ReseñaPlatoRequestDTO dto);

    ReseñaPlatoResponseDTO toDTO(ReseñaPlato reseñaPlato);
}
