package FormatoBase.proyectoJWT.model.dto.Solver;

import FormatoBase.proyectoJWT.model.entity.Driver;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimalRouteResponse implements Serializable {
    private List<AsignacionDto> asignaciones;
    private BigDecimal costoTotal;//Función objetivo
    private List<DriverDto> conductoresAsignados;
}


