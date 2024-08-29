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
public class Proveedores extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "horario_atencion")
    private String horarioAtencion;

    @OneToMany(mappedBy = "idProveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProveedorProducto> proveedorProducto = new ArrayList<>();
}
