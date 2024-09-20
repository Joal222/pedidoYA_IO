package FormatoBase.proyectoJWT.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorUpdateDto {

    @NotNull
    private String horarioAtencion;

    @NotNull
    private String pbx;
}
