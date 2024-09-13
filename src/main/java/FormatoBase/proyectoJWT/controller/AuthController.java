package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.ErrorResponse;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import FormatoBase.proyectoJWT.exception.CustomAuthenticationException;
import FormatoBase.proyectoJWT.exception.DuplicateEmailException;
import FormatoBase.proyectoJWT.exception.InvalidPasswordFormatException;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthResponse;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthenticationRequest;
import FormatoBase.proyectoJWT.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (CustomAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomAuthenticationException(CustomAuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().errorMessage(e.getMessage()).build());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordFormatException(InvalidPasswordFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
