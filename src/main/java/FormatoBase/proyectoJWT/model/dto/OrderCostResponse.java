package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCostResponse implements Serializable {
    private Integer distance;
    private BigDecimal totalCost;
}
