package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {

  private Integer id;
  @NotNull(message = "La descripción no puede ser nulo")
  private String descripcion;
  @NotNull(message = "El nombre no puede ser nulo")
  private String nombreProducto;
  @Min(value = 1, message = "Las dimensiones deben ser mayores que 0")
  private float dimensionM3;
  @Min(value = 1, message = "El peso debe ser mayor que 0")
  private float pesoKg;
  @Min(value = 1, message = "El precio debe ser mayor que 0")
  private float precio;
  @NotNull(message = "La URL de imagen no puede ser nulo")
  private String url;
  private Integer idTipoProducto;  // Solo enviar el ID del TipoProducto

  // Getters y setters
}
