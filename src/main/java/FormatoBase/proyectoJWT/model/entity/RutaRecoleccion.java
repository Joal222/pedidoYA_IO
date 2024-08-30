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
public class RutaRecoleccion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "costoActivacion")
    private float costoActivacion;

    @OneToMany(mappedBy = "idRutaRecoleccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidoList = new ArrayList<>();

    @OneToMany(mappedBy = "idRutaRecoleccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empleado> empleadoList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idVehiculo",referencedColumnName = "id")
    private Vehiculo idVehiculo;
}
