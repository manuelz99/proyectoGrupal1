package grupo.proyecto.Mapper;

import grupo.proyecto.Models.Plato;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface PlatoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", ignore = true)
    @Mapping(target = "reseñas", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    Plato toEntity(PlatoRequestDTO platoRequestDTO);

    PlatoResponseDTO toDTO(Plato plato);
}
