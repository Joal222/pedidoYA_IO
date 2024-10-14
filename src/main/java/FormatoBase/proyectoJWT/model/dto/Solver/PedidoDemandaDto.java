package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDemandaDto implements Serializable {
    private Integer pedidoId;
    private Integer cantidad;
}
