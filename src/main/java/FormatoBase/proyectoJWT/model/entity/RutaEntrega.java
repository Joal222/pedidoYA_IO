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
public class RutaEntrega extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "costoActivacion")
    private float costoActivacion;

    @Column(name = "horario")
    private String horario;

    @OneToMany(mappedBy = "idRutaEntrega", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidoList = new ArrayList<>();

    @OneToMany(mappedBy = "idRutaEntrega", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clientes> clientesList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idVehiculo",referencedColumnName = "id")
    private Vehiculo idVehiculo;
}
