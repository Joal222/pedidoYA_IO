package FormatoBase.proyectoJWT.model.dto.Solver;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionDto implements Serializable {
    private Integer pedidoId;
    private Integer proveedorId;
    private Integer cantidadAsignada;
}
