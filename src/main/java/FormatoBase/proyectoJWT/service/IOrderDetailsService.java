package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.entity.Driver;
import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.model.entity.Proveedores;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderDetailsService {
    BigDecimal calcularCostoPedido(Integer pedidoId, Integer productoId, Integer driverId);
    BigDecimal obtenerDistanciaEnKm(Integer pedidoId, Integer productoId, Integer driverId);

    int[] obtenerDemanda(List<Pedido> pedidos, Integer productoId);
    int[] obtenerOferta(List<Proveedores> proveedores, Integer productoId);
    BigDecimal[][] obtenerCostos(List<Pedido> pedidos, List<Proveedores> proveedores, Integer productoId);

    List<Driver> asignarDrivers(List<Pedido> pedidos, List<Proveedores> proveedores, Integer productoId);
    Driver asignarConductorCercano(List<Driver> conductoresElegibles, List<Proveedores> proveedores, Pedido pedido);
}