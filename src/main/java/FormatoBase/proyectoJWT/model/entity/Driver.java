package FormatoBase.proyectoJWT.model.entity;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "driver")
public class Driver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "limite_capacidad_peso_kg")
    private Float limiteCapacidadKg;

    @Column(name = "limite_capacidad_dimension_m3")
    private Float limiteCapacidadM3;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "costo_activacion")
    private BigDecimal costoActivacion;

    @Column(name = "rendimiento_galon")
    private BigDecimal rendimientoGalon;

    @Column(name = "direccion")
    private String direccion;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idEstado",referencedColumnName = "id")
    private Estado idEstado;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_vehiculo",referencedColumnName = "id")
    private TipoVehiculo idTipoVehiculo;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado",referencedColumnName = "id")
    private Empleado idEmpleado;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_combustible",referencedColumnName = "id")
    private TipoCombustible idTipoCombustible;

    @OneToMany(mappedBy = "idDriver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetailsList = new ArrayList<>();
}
