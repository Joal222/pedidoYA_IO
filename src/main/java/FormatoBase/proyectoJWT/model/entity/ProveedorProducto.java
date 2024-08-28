package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "proveedor_producto")
public class ProveedorProducto extends BaseEntity {



    @Id
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor",referencedColumnName = "idProveedor",nullable = false)
    private Proveedores idProveedor;

    @Id
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto",referencedColumnName = "idProducto",nullable = false)
    private Productos idProducto;

    @Column(name = "disponibilidad")
    private int disponibilidad;
}
