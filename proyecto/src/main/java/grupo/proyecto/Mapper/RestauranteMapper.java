package grupo.proyecto.Mapper;

import grupo.proyecto.Models.Restaurante;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "reseñas", ignore = true)
    @Mapping(target = "latitud", ignore = true)
    @Mapping(target = "longitud", ignore = true)
    Restaurante toEntity(RestauranteRequestDTO dto);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "latitud", target = "latitud")
    @Mapping(source = "longitud", target = "longitud")
    RestauranteResponseDTO toDTO(Restaurante restaurante);
}