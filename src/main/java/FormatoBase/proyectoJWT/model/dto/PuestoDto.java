package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuestoDto {

  private Integer id;
  private String descripcion;
}
