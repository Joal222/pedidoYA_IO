package FormatoBase.proyectoJWT.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {//SecurityFilterChain es una cadena de seguridad donde se van ejecutando el filtrado y la autenticación. Se define la seguridad que se le va a aplicar a los endpoints

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean//Cadena de filtrado, que será la responsable de lanzar nuestro filtro antes del proceso de autenticación
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(publicEndpoints()).permitAll()//Se define que métodos son públicos, lista blanca
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//UsernamePasswordAuthenticationFilter es un filtro que se ejecuta antes de JwtFilter y es propia de Spring Security
        return httpSecurity.build();
    }

    private RequestMatcher publicEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/greeting/sayHelloPublic"),
                new AntPathRequestMatcher("/api/auth/**"),
                new AntPathRequestMatcher("/doc/**", "GET"),
                new AntPathRequestMatcher("/swagger-ui/**", "GET"),
                new AntPathRequestMatcher("/v3/api-docs/**", "GET")
        );
    }
}