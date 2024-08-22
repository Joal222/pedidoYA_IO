package com.jaax.springsecurity.service;

import com.jaax.springsecurity.DTO.AuthResponse;
import com.jaax.springsecurity.DTO.AuthenticationRequest;
import com.jaax.springsecurity.DTO.RegisterRequest;
import com.jaax.springsecurity.entity.Role;
import com.jaax.springsecurity.entity.User;
import com.jaax.springsecurity.exception.CustomAuthenticationException;
import com.jaax.springsecurity.exception.DuplicateEmailException;
import com.jaax.springsecurity.exception.InvalidPasswordFormatException;
import com.jaax.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;//Interfaz propia de Spring Security
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

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
        var jwtToken = jwtService.generateToken(user);//Siempre a base de userDetails
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

            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (UsernameNotFoundException e) {
            throw new CustomAuthenticationException("Usuario no registrado");
        }
    }
}