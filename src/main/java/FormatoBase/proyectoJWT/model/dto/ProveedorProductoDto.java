package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorProductoDto {
  private Integer idProveedor;
  private Integer disponibilidad;
}
