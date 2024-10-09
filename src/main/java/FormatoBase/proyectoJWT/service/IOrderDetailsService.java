package FormatoBase.proyectoJWT.service;

import java.math.BigDecimal;

public interface IOrderDetailsService {
    BigDecimal calcularCostoPedido(Integer pedidoId, Integer productoId, Integer driverId);
    BigDecimal obtenerDistanciaEnKm(Integer pedidoId, Integer productoId, Integer driverId);
}