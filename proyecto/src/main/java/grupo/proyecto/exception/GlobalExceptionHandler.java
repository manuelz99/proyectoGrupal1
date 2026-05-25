package grupo.proyecto.exception;

import grupo.proyecto.Models.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidaciones(MethodArgumentNotValidException ex) {

        ErrorResponseDTO response = new ErrorResponseDTO(LocalDateTime.now(), "Error de valicacion");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarErrorGeneral(Exception ex) {

        ErrorResponseDTO response = new ErrorResponseDTO(LocalDateTime.now(), "Error interno del servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(RecursoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(RecursoNotFoundException ex) {

        ErrorResponseDTO response = new ErrorResponseDTO(LocalDateTime.now(), "Recurso no encontrado");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
