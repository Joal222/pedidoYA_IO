package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.model.repository.DriverRepository;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

        BigDecimal distanciaTotal = calcularDistanciaEntreEntidades(driver, proveedor, pedido);
        return calcularCostoPorDistancia(distanciaTotal, driver);
    }

    @Override
    public BigDecimal obtenerDistanciaEnKm(Integer pedidoId, Integer productoId, Integer driverId) {
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);
        Driver driver = obtenerDriverPorId(driverId);

        return calcularDistanciaEntreEntidades(driver, proveedor, pedido);
    }

    @Override
    public int[] obtenerDemanda(List<Pedido> pedidos, Integer productoId) {
        return pedidos.stream()
                .mapToInt(pedido -> pedido.getPedidoProductoList().stream()
                        .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                        .mapToInt(PedidoProducto::getCantidad)
                        .sum())
                .toArray();
    }

    @Override
    public int[] obtenerOferta(List<Proveedores> proveedores, Integer productoId) {
        return proveedores.stream()
                .mapToInt(proveedor -> proveedor.getProveedorProductoList().stream()
                        .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                        .mapToInt(ProveedorProducto::getDisponibilidad)
                        .sum())
                .toArray();
    }

    @Override
    public BigDecimal[][] obtenerCostos(List<Pedido> pedidos, List<Proveedores> proveedores, Integer productoId, Integer driverId) {
        BigDecimal[][] costos = new BigDecimal[proveedores.size()][pedidos.size()];

        for (int i = 0; i < proveedores.size(); i++) {
            Proveedores proveedor = proveedores.get(i);
            for (int j = 0; j < pedidos.size(); j++) {
                Pedido pedido = pedidos.get(j);
                BigDecimal distanciaTotal = calcularDistanciaEntreEntidades(driverRepo.findById(driverId).get(), proveedor, pedido);
                costos[i][j] = calcularCostoPorDistancia(distanciaTotal, driverRepo.findById(driverId).get());
            }
        }

        return costos;
    }

    private BigDecimal calcularDistanciaEntreEntidades(Driver driver, Proveedores proveedor, Pedido pedido) {
        BigDecimal distanciaDriverAProveedor = googleMapsService.calcularDistancia(
                driver.getLatitud(), driver.getLongitud(),
                proveedor.getLatitud(), proveedor.getLongitud()
        );

        BigDecimal distanciaProveedorACliente = googleMapsService.calcularDistancia(
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
        );

        return distanciaDriverAProveedor.add(distanciaProveedorACliente);
    }

    private BigDecimal calcularCostoPorDistancia(BigDecimal distanciaTotal, Driver driver) {
        BigDecimal precioCombustible = driver.getIdTipoCombustible().getPrecio();
        BigDecimal rendimientoGalon = driver.getRendimientoGalon();
        BigDecimal costoPorGalon = precioCombustible.divide(rendimientoGalon, BigDecimal.ROUND_HALF_UP);
        BigDecimal costoDistancia = costoPorGalon.multiply(distanciaTotal);

        return costoDistancia.add(driver.getCostoActivacion());
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
}

