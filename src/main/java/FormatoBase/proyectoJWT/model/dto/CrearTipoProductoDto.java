package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearTipoProductoDto {
  @NotNull(message = "El nombre no debe ser nulo")
  private String nombre;

  @NotNull(message = "El estado no debe ser nulo")
  private Integer estado;  // Estado como Integer para permitir valores null si es necesario
}
