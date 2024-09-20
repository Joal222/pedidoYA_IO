package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDto {

    private Integer id;

    @NotNull
    private String nombreComercial;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    @NotNull
    private String direccion;

    @NotNull
    private String horarioAtencion;

    @NotNull
    private String pbx;
}
