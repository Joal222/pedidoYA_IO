package FormatoBase.proyectoJWT.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "ruta_recoleccion")
public class RutaRecoleccion {

    @Id
    @Column(name = "id_recoleccion", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecoleccion;

    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @Column(name = "costo_activacion")
    private BigDecimal costoActivacion;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "modificado_por", nullable = false)
    private String modificadoPor;

}
