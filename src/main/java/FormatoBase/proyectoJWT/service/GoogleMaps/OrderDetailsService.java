package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.model.repository.DriverRepository;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    @Override
    public BigDecimal calcularCostoPedido(Integer pedidoId, Integer productoId, Integer driverId) {
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);
        Driver driver = obtenerDriverPorId(driverId);

        BigDecimal distanciaTotal = calcularDistanciaEntreEntidades(driver, proveedor, pedido);
        return calcularCostoPorDistancia(distanciaTotal, driver);
    }

    @Transactional
    @Override
    public BigDecimal obtenerDistanciaEnKm(Integer pedidoId, Integer productoId, Integer driverId) {
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);
        Driver driver = obtenerDriverPorId(driverId);

        return calcularDistanciaEntreEntidades(driver, proveedor, pedido);
    }

    @Transactional
    @Override
    public int[] obtenerDemanda(List<Pedido> pedidos, Integer productoId) {
        return pedidos.stream()
                .mapToInt(pedido -> pedido.getPedidoProductoList().stream()
                        .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                        .mapToInt(PedidoProducto::getCantidad)
                        .sum())
                .toArray();
    }

    @Transactional
    @Override
    public int[] obtenerOferta(List<Proveedores> proveedores, Integer productoId) {
        return proveedores.stream()
                .mapToInt(proveedor -> proveedor.getProveedorProductoList().stream()
                        .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                        .mapToInt(ProveedorProducto::getDisponibilidad)
                        .sum())
                .toArray();
    }

    @Transactional
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

    @Transactional
    @Override
    public List<Driver> asignarDrivers(List<Pedido> pedidos, List<Proveedores> proveedores, Integer productoId) {
        List<Driver> conductoresAsignados = new ArrayList<>();
        List<Driver> driversDisponibles = driverRepo.findAll(); // Obtenemos los conductores disponibles en el momento

        for (Pedido pedido : pedidos) {
            float pesoTotal = (float) pedido.getPedidoProductoList().stream()// Suma el peso y volumen total de los productos del pedido
                    .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                    .mapToDouble(pp -> pp.getIdProducto().getPesoKg() * pp.getCantidad())
                    .sum();

            float volumenTotal = (float) pedido.getPedidoProductoList().stream()
                    .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                    .mapToDouble(pp -> pp.getIdProducto().getDimensionM3() * pp.getCantidad())
                    .sum();

            List<Driver> conductoresElegibles = driversDisponibles.stream()// Filtrar conductores que puedan manejar el peso y volumen del producto
                    .filter(driver -> driver.getLimiteCapacidadKg() >= pesoTotal &&
                            driver.getLimiteCapacidadM3() >= volumenTotal)
                    .collect(Collectors.toList());

            if (conductoresElegibles.isEmpty()) {
                throw new RuntimeException("No hay conductores con capacidad suficiente para manejar el pedido.");
            }

            Driver conductorAsignado = asignarConductorCercano(conductoresElegibles, proveedores, pedido);// Asignar el conductor más cercano
            conductoresAsignados.add(conductorAsignado);

            driversDisponibles.remove(conductorAsignado);  // Eliminar conductor del listado disponible para evitar sobreasignaciones
        }

        return conductoresAsignados;
    }

    @Transactional
    @Override
    public Driver asignarConductorCercano(List<Driver> conductoresElegibles, List<Proveedores> proveedores, Pedido pedido) {
        Driver mejorConductor = null;// Lógica para seleccionar el conductor más cercano
        BigDecimal distanciaMinima = BigDecimal.valueOf(Double.MAX_VALUE);

        for (Driver conductor : conductoresElegibles) {
            for (Proveedores proveedor : proveedores) { // Calcular distancia entre el conductor y el proveedor más cercano
                BigDecimal distanciaConductorAProveedor = googleMapsService.calcularDistancia(
                        conductor.getLatitud(), conductor.getLongitud(),
                        proveedor.getLatitud(), proveedor.getLongitud());

                if (distanciaConductorAProveedor.compareTo(distanciaMinima) < 0) {
                    distanciaMinima = distanciaConductorAProveedor;
                    mejorConductor = conductor;
                }
            }
        }

        return mejorConductor;
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

