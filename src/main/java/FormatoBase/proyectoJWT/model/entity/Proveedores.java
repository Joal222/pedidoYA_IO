package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedores extends BaseEntity {

    @Id
    @Column(name = "id_proveedor", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "horario_atencion")
    private String horario_atencion;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProveedorProducto> proveedorProductosList = new ArrayList<>();
}
