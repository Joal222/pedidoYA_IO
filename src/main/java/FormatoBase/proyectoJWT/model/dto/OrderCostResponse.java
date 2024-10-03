package FormatoBase.proyectoJWT.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCostResponse implements Serializable {
    private Integer distance;
    private Integer totalCost;
}
