package FormatoBase.proyectoJWT.exception;

import org.hibernate.service.spi.ServiceException;
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

  // Manejar excepciones personalizadas de servicio (ServiceException)
  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<Map<String, String>> handleServiceException(ServiceException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Error en la operación de servicio.");
    response.put("detalle", ex.getMessage());

    // Si deseas incluir el stack trace
    Throwable cause = ex.getCause();
    if (cause != null) {
      response.put("causa", cause.toString());
    }

    // Devolver el error en formato JSON
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Manejar excepciones genéricas
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Error interno del sistema.");
    response.put("detalle", ex.getMessage());

    // Devolver el error en formato JSON
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
