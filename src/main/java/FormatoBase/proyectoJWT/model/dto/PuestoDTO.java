package FormatoBase.proyectoJWT.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PuestoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idPuesto;

    private String descripcion;

    private String creadoPor;

    private Date fechaCreacion;

    private Date fechaModificacion;

    private String modificadoPor;

}
