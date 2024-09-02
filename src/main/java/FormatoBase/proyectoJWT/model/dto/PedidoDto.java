package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PedidoDto {

    private Integer id;
    @NotNull

    private Integer IdClientes;

    private String direccionEntrega;
    @NotNull
    private String direccionRecepcion;
    @NotNull
    private String ubicacionEntrega;
    @NotNull
    private String ubicacionRecepcion;

}
