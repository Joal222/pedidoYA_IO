package FormatoBase.proyectoJWT.service.AuthAndRegister;

import FormatoBase.proyectoJWT.exception.CustomAuthenticationException;
import FormatoBase.proyectoJWT.exception.DuplicateEmailException;
import FormatoBase.proyectoJWT.exception.InvalidPasswordFormatException;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.RegisterRequestAdmin;
import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Empleado;
import FormatoBase.proyectoJWT.model.repository.ClientesRepository;
import FormatoBase.proyectoJWT.model.repository.EmpleadoRepository;
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

import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ClientesRepository clientesRepository;
    private final EmpleadoRepository empleadoRepository;
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
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("El correo electrónico ya está en uso");
        }
        validatePassword(request.getPassword());

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        // Guardar el usuario primero
        user = userRepository.save(user);

        // Verificar si la lista clientesList es null y inicializarla si es necesario
        if (user.getClientesList() == null) {
            user.setClientesList(new ArrayList<>());
        }

        var cliente = Clientes.builder()
                .idUser(user)
                .build();

        // Añadir el cliente a la lista del usuario
        user.getClientesList().add(cliente);

        // Guardar el cliente
        clientesRepository.save(cliente);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Override
    public AuthResponse registerAdmin(RegisterRequestAdmin requestAdmin) {
        if (userRepository.findUserByEmail(requestAdmin.getEmail()).isPresent()) {
            throw new DuplicateEmailException("El correo electrónico ya está en uso");
        }
        validatePassword(requestAdmin.getPassword());

        var user = User.builder()
                .firstName(requestAdmin.getFirstName())
                .lastName(requestAdmin.getLastName())
                .email(requestAdmin.getEmail())
                .password(passwordEncoder.encode(requestAdmin.getPassword()))
                .role(Role.ADMIN)
                .build();

        // Guardar el usuario primero
        user = userRepository.save(user);

        // Verificar si la lista empleadoList es null y inicializarla si es necesario
        if (user.getEmpleadoList() == null) {
            user.setEmpleadoList(new ArrayList<>());
        }

        var empleado = Empleado.builder()
                .idUser(user)
                .direccion(requestAdmin.getDireccion())
                .dpi(requestAdmin.getDpi())
                .telefono(requestAdmin.getTelefono())
                .build();

        // Añadir el empleado a la lista del usuario
        user.getEmpleadoList().add(empleado);

        // Guardar el empleado
        empleadoRepository.save(empleado);

        var jwtToken = jwtService.generateToken(user);
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

            // Generar el token incluyendo el rol del usuario
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (UsernameNotFoundException e) {
            throw new CustomAuthenticationException("Usuario no registrado");
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado durante la autenticación: " + e.getMessage());
        }
    }


}
