package FormatoBase.proyectoJWT.model.entity.AuthAndRegister;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name ="rol")
public class Rol extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre_rol")
    private String nombreRol;

    @Column(name = "descripcion")
    private String descripcion;

    /*@OneToMany(mappedBy = "rolId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Users> usersList = new ArrayList<>();*/
}
