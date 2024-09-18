package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto {
  private Integer id;
  private String telefono;
  private String telefonoEmergencia;
  private int idUser;
  private String firstName;
  private String lastName;
  private String email;
}
