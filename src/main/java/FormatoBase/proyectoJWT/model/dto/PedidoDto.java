package FormatoBase.proyectoJWT.model.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto implements Serializable {
   
    private Integer id;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Integer idClientes;

    @NotNull(message = "El ID del estado no puede ser nulo")
    private Integer idEstado;

    @NotNull(message = "El nombre del Cliente no puede ser nulo")
    private String clientePedido;

    @NotNull(message = "El telefono del Cliente no puede ser nulo")
    private String telefonoPedido;

    private LocalDateTime fechaCreacion;

    @NotNull(message = "La dirección de entrega no puede ser nula")
    private String direccionEntrega;

    @NotNull(message = "La latitud no puede ser nula")
    private String latitud;

    @NotNull(message = "La longitud no puede ser nula")
    private String longitud;

    @NotNull(message = "Debe haber al menos un producto en el pedido")
    private List<PedidoProductoDto> pedidoProductoList;  // Para validar que no sea nulo
}

