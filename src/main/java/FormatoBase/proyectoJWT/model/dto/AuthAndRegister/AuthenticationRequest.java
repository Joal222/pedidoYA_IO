package FormatoBase.proyectoJWT.model.dto.AuthAndRegister;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
    private Role role;
}
