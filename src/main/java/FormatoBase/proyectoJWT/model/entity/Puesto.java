package FormatoBase.proyectoJWT.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "puesto")
public class Puesto implements Serializable {

    @Id
    @Column(name = "id_puesto", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuesto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "modificado_por", nullable = false)
    private String modificadoPor;

}
