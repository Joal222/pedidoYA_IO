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
public class Productos extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre", nullable = false)
    private String nombreProducto;

    @Column(name = "dimensionM3")
    private float dimensionM3;

    @Column(name = "pesoKg")
    private float pesoKg;

    @Column(name = "precio", nullable = false)
    private float precio;

    @Column(name = "tipo_producto", nullable = false)
    private String tipoProducto;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProveedorProducto> proveedorProductoList = new ArrayList<>();

    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteProducto> clienteProductoList = new ArrayList<>();

    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProducto> pedidoProductoList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoProducto",referencedColumnName = "id")
    private TipoProducto idTipoProducto;
}
