package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCostRequest implements Serializable {

    private Integer pedidoId;
    private Integer productoId;
    private Integer driverId;
}
