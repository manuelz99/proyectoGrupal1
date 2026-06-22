package grupo.proyecto.Models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private String mensaje;

    public ErrorResponseDTO(String mensaje) {
        this.timestamp = LocalDateTime.now();
        this.mensaje = mensaje;
    }
}
