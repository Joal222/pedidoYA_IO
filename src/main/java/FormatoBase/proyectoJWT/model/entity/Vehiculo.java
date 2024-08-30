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
public class Vehiculo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "limiteCapacidad")
    private float limiteCapacidad;

    @Column(name = "ubicacion")
    private String ubicacion;

    @OneToMany(mappedBy = "idVehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RutaEntrega> rutaEntregaList = new ArrayList<>();

    @OneToMany(mappedBy = "idVehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RutaRecoleccion> rutaRecoleccionList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_vehiculo",referencedColumnName = "id")
    private TipoVehiculo idTipoVehiculo;
}
