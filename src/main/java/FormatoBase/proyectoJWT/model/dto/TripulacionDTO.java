package FormatoBase.proyectoJWT.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TripulacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idEmpleado;

    private Long idRutaEntrega;

    private String creadoPor;

    private Date fechaCreacion;

    private Date fechaModificacion;

    private String modificadoPor;

}
