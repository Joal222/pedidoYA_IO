package FormatoBase.proyectoJWT.model.entity;

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
public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "idTipoVehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculoList = new ArrayList<>();
}
