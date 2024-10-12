package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionDto implements Serializable {
    private Integer pedidoId;//Cliente Id
    private Integer proveedorId;//Proveedor Id
    private Integer cantidadAsignada; //Cantidad de producto
    private BigDecimal costoTransporte;
}
