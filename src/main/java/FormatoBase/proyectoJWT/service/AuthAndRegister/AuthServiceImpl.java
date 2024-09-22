package FormatoBase.proyectoJWT.service.AuthAndRegister;

import FormatoBase.proyectoJWT.exception.CustomAuthenticationException;
import FormatoBase.proyectoJWT.exception.DuplicateEmailException;
import FormatoBase.proyectoJWT.exception.InvalidPasswordFormatException;
import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.repository.ClientesRepository;
import FormatoBase.proyectoJWT.model.repository.UserRepository;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthResponse;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.AuthenticationRequest;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.RegisterRequest;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.Role;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.User;
import FormatoBase.proyectoJWT.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;//Interfaz propia de Spring Security
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&()-+=.,])(?=\\S+$).{6,}$");


    private void validatePassword(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordFormatException(
                    "Formato de contraseña inválido: Mínimo de 6 caracteres, debe incluir al menos una letra mayúscula, un carácter especial y un número");
        }
    }

    @Override
    public AuthResponse register(RegisterRequest request) {//Método para generar el token después del registro del usuario.
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {// Verificar si el correo electrónico ya existe
            throw new DuplicateEmailException("El correo electrónico ya está en uso");
        }
        validatePassword(request.getPassword());// Validar la contraseña
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))//Encriptación del password
                .role(Role.USER)//Por default se coloco USER = usuario normal, modificar lógica según requerimiento.
                .build();
        userRepository.save(user);

        // Crear y guardar el cliente relacionado con el nuevo usuario
        var cliente = Clientes.builder()
                .idUser(user) // Asociar el cliente con el usuario recién creado
                .build();
        clientesRepository.save(cliente); // Guardar el cliente
        var jwtToken = jwtService.generateToken(Map.of("id", user.getId()), user);//Siempre a base de userDetails
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        try {
            var user = userRepository.findUserByEmail(request.getEmail())
                    .orElseThrow(() -> new CustomAuthenticationException("Usuario no registrado"));

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
            } catch (BadCredentialsException e) {
                throw new CustomAuthenticationException("Contraseña incorrecta");
            }

            var jwtToken = jwtService.generateToken(Map.of("id", user.getId()), user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (UsernameNotFoundException e) {
            throw new CustomAuthenticationException("Usuario no registrado");
        } catch (Exception e) {
            // Captura cualquier excepción genérica inesperada
            throw new RuntimeException("Error inesperado durante la autenticación: " + e.getMessage());
        }
    }

}
