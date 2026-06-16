package grupo.proyecto.Models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NominatimResponse {

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lon")
    private String lon;
}
