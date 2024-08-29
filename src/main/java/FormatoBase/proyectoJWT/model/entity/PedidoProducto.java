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
@Table(name = "pedido_producto")
public class PedidoProducto extends BaseEntity {

    @EmbeddedId
    private PedidoProductoId id;

    @JsonIgnore
    @MapsId("idPedido")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", referencedColumnName = "idPedido")
    private Pedidos pedido;

    @JsonIgnore
    @MapsId("idProducto")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", referencedColumnName = "idProducto")
    private Productos producto;

    @Column(name = "cantidad")
    private int cantidad;
}
