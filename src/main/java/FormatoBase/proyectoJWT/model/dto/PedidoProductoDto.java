package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoDto implements Serializable {
    private Integer id;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Integer idProducto;

    @NotNull(message = "La cantidad no puede ser nula")
    private Integer cantidad;
}
