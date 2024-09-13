package FormatoBase.proyectoJWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Manejar excepciones de validación (MethodArgumentNotValidException)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    // Recorrer la lista de errores y asignar el campo y su respectivo mensaje de error
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    // Devolver los errores en formato JSON
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  // Puedes agregar más métodos para manejar otras excepciones
  // Por ejemplo, manejar excepciones genéricas
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGlobalException(Exception ex) {
    // Devolver un mensaje genérico de error en formato JSON
    return new ResponseEntity<>("Error interno del sistema: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
