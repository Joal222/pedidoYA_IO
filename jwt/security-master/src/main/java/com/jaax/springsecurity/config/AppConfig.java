package com.jaax.springsecurity.config;

import com.jaax.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;
    @Bean // Para que Spring lo pueda inyectar
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                return userRepository.findUserByEmail(username)// Buscar el usuario en el repositorio
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrdao for userDetailsService"));
            } catch (UsernameNotFoundException e) {
                System.out.println("User not found: " + e.getMessage());
                throw e;
            }
        };
    }

    @Bean//Se utiliza en SecurityConfig, este es el proveedor de autenticación
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean//Para encriptar las claves que generemos
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Bean//Para que la inyección de la interfaz an el AuthServiceImpl pueda utilizarse
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
    return config.getAuthenticationManager();//Es una clase que nos permite gestionar la autenticación por medio de una request(usuario, password)
    }
}
