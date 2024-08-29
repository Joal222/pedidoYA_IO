package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "cliente_producto")
public class ClienteProducto extends BaseEntity {

    @EmbeddedId
    private ClienteProductoId id;

    @JsonIgnore
    @MapsId("idProducto")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto",referencedColumnName = "idProducto",nullable = false)
    private Productos producto;

    @JsonIgnore
    @MapsId("idCliente")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente",referencedColumnName ="idCliente", nullable = false)
    private Clientes cliente;

    @Column(name = "disponibilidad")
    private int disponibilidad;
}
