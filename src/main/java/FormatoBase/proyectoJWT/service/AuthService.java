package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthResponse;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthenticationRequest;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.RegisterRequest;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.RegisterRequestAdmin;

public interface AuthService {

    AuthResponse register(RegisterRequest request );

    AuthResponse registerAdmin(RegisterRequestAdmin requestAdmin);

    AuthResponse authenticate(AuthenticationRequest request);
}