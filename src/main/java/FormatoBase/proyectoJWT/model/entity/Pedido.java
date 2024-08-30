package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
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

    @Column(name = "direccionEntrega")
    private String direccionEntrega;

    @Column(name = "direccionRecepcion")
    private String direccionRecepcion;

    @Column(name = "ubicacionEntrega")
    private String ubicacionEntrega;

    @Column(name = "ubicacionRecepcion")
    private String ubicacionRecepcion;

    @OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProducto> pedidoProductoList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstado",referencedColumnName = "id")
    private Estado idEstado;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente",referencedColumnName = "id")
    private Clientes idClientes;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta_entrega",referencedColumnName = "id")
    private RutaEntrega idRutaEntrega;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta_recoleccion",referencedColumnName = "id")
    private RutaRecoleccion idRutaRecoleccion;
}
