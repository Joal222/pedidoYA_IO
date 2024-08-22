package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthResponse;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthenticationRequest;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request );

    AuthResponse authenticate(AuthenticationRequest request);
}