package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "direccionEntrega")
    private String direccionEntrega;

    @NotNull
    @Column(name = "direccionRecepcion")
    private String direccionRecepcion;

    @NotNull
    @Column(name = "ubicacionEntrega")
    private String ubicacionEntrega;

    @NotNull
    @Column(name = "ubicacionRecepcion")
    private String ubicacionRecepcion;

    @OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoProducto> pedidoProductoList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idEstado",referencedColumnName = "id")
    private Estado idEstado;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente",referencedColumnName = "id")
    private Clientes idClientes;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta_entrega",referencedColumnName = "id")
    private RutaEntrega idRutaEntrega;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta_recoleccion",referencedColumnName = "id")
    private RutaRecoleccion idRutaRecoleccion;
}
