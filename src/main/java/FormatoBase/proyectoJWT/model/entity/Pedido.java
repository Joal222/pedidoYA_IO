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
public class Pedido extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "clientePedido")
    private String clientePedido;

    @Column(name = "telefonoPedido")
    private String telefonoPedido;

    @Column(name = "direccionEntrega")
    private String direccionEntrega;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente",referencedColumnName = "id")
    private Clientes idClientes;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idEstado",referencedColumnName = "id")
    private Estado idEstado;

    @OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoProducto> pedidoProductoList = new ArrayList<>();

    @OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetailsList = new ArrayList<>();

    /*@NotNull
    @Column(name = "ubicacionEntrega")
    private String ubicacionEntrega;*/

    /*@NotNull
    @Column(name = "ubicacionRecepcion")
    private String ubicacionRecepcion; */
}
