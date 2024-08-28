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
@Table(name = "productos")
public class Productos extends BaseEntity {

    @Id
    @Column(name = "id_producto", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "tipo_producto", nullable = false)
    private String tipoProducto;

    @Column(name = "precio", nullable = false)
    private float precio;

    @Column(name = "cantidad_disponible")
    private int cantidadDisponible;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "idProducto",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ClienteProducto> clienteProductosList = new ArrayList<>();

    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProveedorProducto> proveedorProductosList = new ArrayList<>();
}
