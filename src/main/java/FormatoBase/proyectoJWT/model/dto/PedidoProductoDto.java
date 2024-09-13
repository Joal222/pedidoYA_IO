package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoDto implements Serializable {
    private Integer id;
    private Integer idProducto;
    private Integer cantidad;
    private String nombreProducto;
    private String urlImagen;
}
