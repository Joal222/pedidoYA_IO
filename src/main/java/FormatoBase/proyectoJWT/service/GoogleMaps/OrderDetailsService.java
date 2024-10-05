package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.model.repository.DriverRepository;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepo;

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private DriverRepository driverRepo;

    @Override
    public BigDecimal calcularCostoPedido(Integer pedidoId, Integer productoId, Integer driverId) {

        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);
        Driver driver = obtenerDriverPorId(driverId);
        
        Integer distanciaDriverAProveedor = googleMapsService.calcularDistancia(
                driver.getLatitud(), driver.getLongitud(),
                proveedor.getLatitud(), proveedor.getLongitud()
        );

        Integer distanciaProveedorACliente = googleMapsService.calcularDistancia(
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
        );

        Integer distanciaTotal = distanciaDriverAProveedor + distanciaProveedorACliente;

        BigDecimal precioCombustible = driver.getIdTipoCombustible().getPrecio();
        BigDecimal rendimientoGalon = driver.getRendimientoGalon();
        BigDecimal costoPorGalon = precioCombustible.divide(rendimientoGalon, BigDecimal.ROUND_HALF_UP);
        BigDecimal costoDistancia = costoPorGalon.multiply(BigDecimal.valueOf(distanciaTotal));
        BigDecimal costoActivacion = driver.getCostoActivacion();

        return calcularCostoTotal(costoDistancia, costoActivacion);
    }

    @Override
    public Integer obtenerDistanciaEnKm(Integer pedidoId, Integer productoId) {
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);

        return googleMapsService.calcularDistancia(
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
        );
    }

    private Pedido obtenerPedidoPorId(Integer pedidoId) {
        return pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    private Proveedores obtenerProveedorPorProducto(Integer productoId) {
        ProveedorProducto proveedorProducto = proveedorProductoRepo.findByIdProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en ningún proveedor"));
        return proveedorProducto.getIdProveedor();
    }

    private Driver obtenerDriverPorId(Integer driverId) {
        return driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
    }

    private BigDecimal calcularCostoTotal(BigDecimal costoDistancia, BigDecimal costoActivacion) {
        return costoDistancia.add(costoActivacion);
    }
}
