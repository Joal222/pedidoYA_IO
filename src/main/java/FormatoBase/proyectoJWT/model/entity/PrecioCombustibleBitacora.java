package FormatoBase.proyectoJWT.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "precio_combustible_bitacora")
public class PrecioCombustibleBitacora {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "id_tipo_combustible")
  private Integer idTipoCombustible;

  @Column(name = "precio_anterior")
  private BigDecimal precioAnterior;

  @Column(name = "fecha_actualizacion")
  private LocalDateTime fechaActualizacion;
}
