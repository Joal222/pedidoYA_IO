package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto implements Serializable {

    private Integer id;

    @NotNull
    private Integer idClientes;

    @NotNull
    private String direccionEntrega;

    @NotNull
    private String direccionRecepcion;

    @NotNull
    private String ubicacionEntrega;

    @NotNull
    private String ubicacionRecepcion;

    private Integer idEstado;

    private Integer idRutaEntrega;

    private Integer idRutaRecoleccion;

    private List<PedidoProductoDto> pedidoProductoList;
}
