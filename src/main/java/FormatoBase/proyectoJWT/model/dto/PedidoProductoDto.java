package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoDto {
    private Integer id;
    private Integer idProducto;
    private Integer cantidad;
    private String nombreProducto;
    private String urlImagen;
}
