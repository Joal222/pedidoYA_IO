package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "url")
    private String url;

    @JsonIgnore
    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProveedorProducto> proveedorProductoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClienteProducto> clienteProductoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoProducto> pedidoProductoList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idTipoProducto",referencedColumnName = "id")
    private TipoProducto idTipoProducto;
}
