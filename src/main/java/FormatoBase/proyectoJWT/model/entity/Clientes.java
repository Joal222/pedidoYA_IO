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
public class Clientes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "direccion")
    private String direccion;

    @OneToMany(mappedBy = "idClientes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteProducto> clienteProductoList = new ArrayList<>();

    @OneToMany(mappedBy = "idClientes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidoList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta_entrega",referencedColumnName = "id")
    private RutaEntrega idRutaEntrega;
}
