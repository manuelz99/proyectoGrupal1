package grupo.proyecto.Mapper;

import grupo.proyecto.Models.ReseñaRestaurant;
import grupo.proyecto.Models.dto.request.ReseñaRestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.ReseñaRestaurtanteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReseñaRestauranteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    ReseñaRestaurant toEntity(ReseñaRestauranteRequestDTO dto);

    ReseñaRestaurtanteResponseDTO toDTO(ReseñaRestaurant reseñaRestaurant);
}
