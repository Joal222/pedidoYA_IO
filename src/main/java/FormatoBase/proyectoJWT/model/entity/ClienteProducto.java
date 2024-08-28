package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "cliente_producto")
public class ClienteProducto extends BaseEntity {

    @Id
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Id
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto",referencedColumnName = "idProducto",nullable = false)
    private Productos idProducto;

    @Column(name = "disponibilidad")
    private int disponibilidad;


}
