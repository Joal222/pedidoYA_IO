package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoVehiculoDto {
  private Integer id;
  private String nombre;
}
