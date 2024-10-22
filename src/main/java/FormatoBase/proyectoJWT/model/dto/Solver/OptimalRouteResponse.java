package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimalRouteResponse implements Serializable {

    private List<AsignacionDto> asignaciones;
    private BigDecimal costoTotal; // Función objetivo
    private List<DriverDtoSolver> conductoresAsignados;

    // Bloque de inicialización para evitar null
    {
        this.asignaciones = new ArrayList<>();
        this.conductoresAsignados = new ArrayList<>();
        this.costoTotal = BigDecimal.ZERO;
    }
}


