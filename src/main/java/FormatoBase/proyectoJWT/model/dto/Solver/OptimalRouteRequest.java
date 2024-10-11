package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimalRouteRequest implements Serializable {
    private List<PedidoDemandaDto> pedidos;
    private List<ProveedorOfertaDto> proveedores;
    private Integer productoId;
}

