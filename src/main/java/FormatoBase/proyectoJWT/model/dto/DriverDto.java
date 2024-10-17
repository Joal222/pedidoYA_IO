package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
  private Integer id;

  @NotNull(message = "El modelo es obligatorio")
  private String modelo;

  @NotNull(message = "La marca es obligatoria")
  private String marca;

  @NotNull(message = "El límite de capacidad es obligatorio")
  private float limiteCapacidadM3;

  @NotNull(message = "El límite de capacidad es obligatorio")
  private float limiteCapacidadKg;

  @NotNull(message = "La latitud es obligatoria")
  private Double latitud;

  @NotNull(message = "La longitud es obligatoria")
  private Double longitud;

  @NotNull(message = "El costo de activación es obligatorio")
  private BigDecimal costoActivacion;

  @NotNull(message = "El rendimiento por galón es obligatorio")
  private BigDecimal rendimientoGalon;

  @NotNull(message = "La dirección es obligatoria")
  private String direccion;

  private Integer idEstado;

  @NotNull(message = "El ID del tipo de vehículo es obligatorio")
  private Integer idTipoVehiculo;

  @NotNull(message = "El ID del empleado es obligatorio")
  private Integer idEmpleado;

  @NotNull(message = "El ID del tipo de combustible es obligatorio")
  private Integer idTipoCombustible;
}
