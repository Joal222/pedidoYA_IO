package FormatoBase.proyectoJWT.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VehiculosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idVehiculo;

    private String modelo;

    private String marca;

    private BigDecimal costoActivacion;

    private BigDecimal limiteCarga;

    private String creadoPor;

    private Date fechaCreacion;

    private Date fechaModificacion;

    private String modificadoPor;

}
