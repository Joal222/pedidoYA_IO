package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCostResponse implements Serializable {
    private BigDecimal distance;
    private BigDecimal totalCost;
}
