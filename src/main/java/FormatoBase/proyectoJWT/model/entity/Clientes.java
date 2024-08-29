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
@Table(name = "clientes")
public class Clientes extends BaseEntity{
    @Id
    @Column(name = "id_cliente", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(name = "id_usuario")
    private int idUsuario;

    @OneToMany(mappedBy = "idCliente",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ClienteProducto> clienteProductosList = new ArrayList<>();

    @OneToMany(mappedBy = "idCliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pedidos> pedidosList = new ArrayList<>();
}
