package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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

  @NotNull(message = "Las dimensiones no pueden ser nulas")
  @Min(value = 1, message = "Las dimensiones deben ser mayores que 0")
  private Float dimensionM3;

  @NotNull(message = "El peso no puede ser nulo")
  @Min(value = 1, message = "El peso debe ser mayor que 0")
  private Float pesoKg;

  @NotNull(message = "El precio no puede ser nulo")
  @Min(value = 1, message = "El precio debe ser mayor que 0")
  private Float precio;

  @NotNull(message = "La URL de imagen no puede ser nulo")
  private String url;

  private Integer idTipoProducto;

  // Cambia de List<Integer> a List<ProveedorCantidadDto>
  private List<ProveedorProductoDto> proveedores;
}

