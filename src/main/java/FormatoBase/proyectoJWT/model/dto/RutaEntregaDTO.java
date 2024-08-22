package FormatoBase.proyectoJWT.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RutaEntregaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idRutaEntrega;

    private Long idVehiculo;

    private BigDecimal costoActivacion;

    private String creadoPor;

    private Date fechaCreacion;

    private Date fechaModificacion;

    private String modificadoPor;

}
