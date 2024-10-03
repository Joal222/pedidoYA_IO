package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorCantidadDto {
  private Integer idProveedor;
  private Integer disponibilidad;
}
