package FormatoBase.proyectoJWT.service;

public interface IOrderDetailsService {
    Integer calcularCostoPedido(Integer pedidoId, Integer productoId, Integer tipoCombustibleId, Integer driverId);
    Integer obtenerDistanciaEnKm(Integer pedidoId, Integer productoId);
}
