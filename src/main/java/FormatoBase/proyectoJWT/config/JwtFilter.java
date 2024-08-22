package FormatoBase.proyectoJWT.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import FormatoBase.proyectoJWT.service.AuthAndRegister.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; //UserDetailsService es una interfaz/Core propia de Spring Security que se utiliza para recuperar los detalles del usuario
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,//Es lo que el cliente esta manando en su petición Http
                                    @NonNull HttpServletResponse response, //Lo que se le entregará al cliente una vez procesada la petición
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {//si no hay un jwt o no empieza con Bearer se responde con status 403
            filterChain.doFilter(request, response);
            return;
        }//Si hay un jwt y empieza con Bearer se obtiene el jwt y se obtiene el email del usuario
        jwt = authHeader.substring(7); //A partir del caracter 7 se encuentra el jwt ejemplo Bearer eyJfadflikjalkdfi
        try {
            userEmail = jwtService.getUserName(jwt);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid Token");
            return;
        }
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {//Validadndo que el email no sea nulo y que no haya una autenticación previa
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); //loadUserByUsername es un método de la interfaz UserDetailsService que se implementa en la clase UserDetailsServiceImpl
            if (jwtService.validateToken(jwt, userDetails)) {//Validando que el jwt sea válido, si no se reponde con un estatus 403
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(//Se crea un objeto UsernamePasswordAuthenticationToken
                        userDetails,//Se le pasa el usuario
                        null,//credenciales
                        userDetails.getAuthorities()//Permisos del usuario
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//Los detalles vienen desntro de la petición
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);//Se establece la autenticación
            }
        }
        filterChain.doFilter(request, response);
    }
}
