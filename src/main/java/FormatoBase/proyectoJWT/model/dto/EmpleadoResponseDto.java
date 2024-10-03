package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDto {
  private Integer id;
  private String direccion;
  private String telefono;
  private String dpi;
  private int idUser;
  private String firstName;
  private String lastName;
  private String email;
}
