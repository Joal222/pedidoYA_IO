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
@Table(name = "pedidos")
public class Pedidos implements Serializable {

    @Id
    @Column(name = "id_pedido", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @Column(name = "direccion_recepcion")
    private String direccionRecepcion;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "modificado_por", nullable = false)
    private String modificadoPor;

}
