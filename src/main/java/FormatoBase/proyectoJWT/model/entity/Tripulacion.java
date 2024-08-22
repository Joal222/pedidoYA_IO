package FormatoBase.proyectoJWT.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "tripulacion")
public class Tripulacion implements Serializable {

    @Id
    @Column(name = "id_empleado", nullable = false)
    private Long idEmpleado;

    @Column(name = "id_ruta_entrega", nullable = false)
    private Long idRutaEntrega;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "modificado_por", nullable = false)
    private String modificadoPor;

}
