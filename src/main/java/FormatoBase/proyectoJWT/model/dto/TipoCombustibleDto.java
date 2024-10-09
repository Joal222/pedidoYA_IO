package FormatoBase.proyectoJWT.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoCombustibleDto {
  private Integer id;
  private String nombre;
  private BigDecimal precio;
}
