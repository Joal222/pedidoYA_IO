package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoCantidadDto {

  private Integer id;
  @NotNull(message = "El nombre del producto no debe ser nulo")
  private String nombre;
  private int estado;
  private Integer cantidadProductos;
}
