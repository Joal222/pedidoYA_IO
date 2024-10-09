package FormatoBase.proyectoJWT.model.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrecioCombustibleBitacoraDto {
  private String nombreTipoCombustible;
  private BigDecimal precioAnterior;
  private LocalDateTime fechaActualizacion;
}
