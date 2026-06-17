package grupo.proyecto.exception;

import grupo.proyecto.Models.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidaciones(MethodArgumentNotValidException ex) {

        ErrorResponseDTO response = new ErrorResponseDTO(LocalDateTime.now(), "Error de valicacion");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }*/

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
    @ExceptionHandler(IdDuplicadoExc.class)
    public ResponseEntity<ErrorResponseDTO> handleIdDuplicado(IdDuplicadoExc ex) {

        ErrorResponseDTO response = new ErrorResponseDTO(LocalDateTime.now(), "El id ya existe en la BD");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

}
