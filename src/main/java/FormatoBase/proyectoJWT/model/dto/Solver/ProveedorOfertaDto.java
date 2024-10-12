package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorOfertaDto implements Serializable {
    private Integer proveedorId;
    private Integer capacidad;
    private BigDecimal costoTransporte;
}