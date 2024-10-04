package FormatoBase.proyectoJWT.model.dto.AuthAndRegister;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestAdmin {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String direccion; // Nuevo campo
  private String telefono;  // Nuevo campo
  private String dpi;       // Nuevo campo
}
