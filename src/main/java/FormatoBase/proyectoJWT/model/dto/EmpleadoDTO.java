package FormatoBase.proyectoJWT.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EmpleadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idEmpleado;

    private Long idPuesto;

    private Long idUsuario;

    private String nombres;

    private String direccion;

    private String telefono;

    private String creadoPor;

    private Date fechaCreacion;

    private Date fechaModificacion;

    private String modificadoPor;

}
