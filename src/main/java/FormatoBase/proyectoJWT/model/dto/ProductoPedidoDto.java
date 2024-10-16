package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPedidoDto {
  private Integer id;
  private String nombreProducto;
  private List<PedidoDto> pedidos;
}
