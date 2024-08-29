package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "pedidos")
public class Pedidos extends BaseEntity {

    @Id
    @Column(name = "id_pedido", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente")
    private Clientes idCliente;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @Column(name = "direccion_recepcion")
    private String direccionRecepcion;

    @OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoProducto> pedidoProductosList = new ArrayList<>();

}
