package FormatoBase.proyectoJWT.service;

import java.math.BigDecimal;

public interface IOrderDetailsService {
    BigDecimal calcularCostoPedido(Integer pedidoId, Integer productoId, Integer driverId);
    Integer obtenerDistanciaEnKm(Integer pedidoId, Integer productoId);
}