package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDtoSolver {
    private Integer driverId;
    private String nombre;
    private String modeloVehiculo;
    private Float limiteCapacidadKg;
    private Float limiteCapacidadM3;
    private BigDecimal costoActivacion;
}
