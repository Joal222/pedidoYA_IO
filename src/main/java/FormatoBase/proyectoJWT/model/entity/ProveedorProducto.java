package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "proveedor_producto")
public class ProveedorProducto extends BaseEntity {

    @EmbeddedId
    private ProveedorProductoId id;

    @JsonIgnore
    @MapsId("idProveedor")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor",referencedColumnName = "idProveedor",nullable = false)
    private Proveedores proveedor;

    @JsonIgnore
    @MapsId("idProducto")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto",referencedColumnName = "idProducto",nullable = false)
    private Productos producto;

    @Column(name = "disponibilidad")
    private int disponibilidad;
}
