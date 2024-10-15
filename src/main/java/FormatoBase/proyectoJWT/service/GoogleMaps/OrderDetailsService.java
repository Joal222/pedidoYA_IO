package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.model.repository.DriverRepository;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.IOrderDetailsService;
import org.hibernate.service.spi.ServiceException;
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
        try {
            Pedido pedido = obtenerPedidoPorId(pedidoId);
            Proveedores proveedor = obtenerProveedorPorProducto(productoId);
            Driver driver = obtenerDriverPorId(driverId);

            BigDecimal distanciaTotal = calcularDistanciaEntreEntidades(driver, proveedor, pedido);
            return calcularCostoPorDistancia(distanciaTotal, driver);
        } catch (Exception e) {
            throw new ServiceException("Error al calcular el costo del pedido.", e);
        }
    }

    @Transactional
    @Override
    public BigDecimal obtenerDistanciaEnKm(Integer pedidoId, Integer productoId, Integer driverId) {
        try {
            Pedido pedido = obtenerPedidoPorId(pedidoId);
            Proveedores proveedor = obtenerProveedorPorProducto(productoId);
            Driver driver = obtenerDriverPorId(driverId);

            return calcularDistanciaEntreEntidades(driver, proveedor, pedido);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener la distancia en km.", e);
        }
    }

    @Transactional
    @Override
    public int[] obtenerDemanda(List<Pedido> pedidos, Integer productoId) {
        try {
            return pedidos.stream()
                    .mapToInt(pedido -> pedido.getPedidoProductoList().stream()
                            .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                            .mapToInt(PedidoProducto::getCantidad)
                            .sum())
                    .toArray();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener la demanda del producto.", e);
        }
    }

    @Transactional
    @Override
    public int[] obtenerOferta(List<Proveedores> proveedores, Integer productoId) {
        try {
            return proveedores.stream()
                    .mapToInt(proveedor -> proveedor.getProveedorProductoList().stream()
                            .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                            .mapToInt(ProveedorProducto::getDisponibilidad)
                            .sum())
                    .toArray();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener la oferta del producto.", e);
        }
    }

    @Transactional
    @Override
    public BigDecimal[][] obtenerCostos(List<Pedido> pedidos, List<Proveedores> proveedores, Integer productoId) {
        try {
            List<Driver> conductoresAsignados = asignarDrivers(pedidos, proveedores, productoId);
            BigDecimal[][] costos = new BigDecimal[proveedores.size()][pedidos.size()];

            for (int i = 0; i < proveedores.size(); i++) {
                Proveedores proveedor = proveedores.get(i);
                for (int j = 0; j < pedidos.size(); j++) {
                    Pedido pedido = pedidos.get(j);
                    Driver conductorAsignado = conductoresAsignados.get(j);
                    BigDecimal distanciaTotal = calcularDistanciaEntreEntidades(conductorAsignado, proveedor, pedido);
                    costos[i][j] = calcularCostoPorDistancia(distanciaTotal, conductorAsignado);
                }
            }
            return costos;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener los costos.", e);
        }
    }

    @Transactional
    @Override
    public List<Driver> asignarDrivers(List<Pedido> pedidos, List<Proveedores> proveedores, Integer productoId) {
        List<Driver> conductoresAsignados = new ArrayList<>();
        try {
            List<Driver> driversDisponibles = driverRepo.findAll();

            for (Pedido pedido : pedidos) {
                float pesoTotal = (float) pedido.getPedidoProductoList().stream()
                        .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                        .mapToDouble(pp -> pp.getIdProducto().getPesoKg() * pp.getCantidad())
                        .sum();

                float volumenTotal = (float) pedido.getPedidoProductoList().stream()
                        .filter(pp -> pp.getIdProducto().getId().equals(productoId))
                        .mapToDouble(pp -> pp.getIdProducto().getDimensionM3() * pp.getCantidad())
                        .sum();

                List<Driver> conductoresElegibles = driversDisponibles.stream()
                        .filter(driver -> driver.getLimiteCapacidadKg() >= pesoTotal &&
                                driver.getLimiteCapacidadM3() >= volumenTotal)
                        .collect(Collectors.toList());

                if (conductoresElegibles.isEmpty()) {
                    throw new ServiceException("No hay conductores con capacidad suficiente para manejar el pedido.");
                }

                Driver conductorAsignado = asignarConductorCercano(conductoresElegibles, proveedores, pedido);
                conductoresAsignados.add(conductorAsignado);

                driversDisponibles.remove(conductorAsignado);
            }

            return conductoresAsignados;
        } catch (Exception e) {
            throw new ServiceException("Error al asignar conductores.", e);
        }
    }

    @Transactional
    @Override
    public Driver asignarConductorCercano(List<Driver> conductoresElegibles, List<Proveedores> proveedores, Pedido pedido) {
        try {
            Driver mejorConductor = null;
            BigDecimal distanciaMinima = BigDecimal.valueOf(Double.MAX_VALUE);

            for (Driver conductor : conductoresElegibles) {
                for (Proveedores proveedor : proveedores) {
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
        } catch (Exception e) {
            throw new ServiceException("Error al asignar el conductor más cercano.", e);
        }
    }

    private BigDecimal calcularDistanciaEntreEntidades(Driver driver, Proveedores proveedor, Pedido pedido) {
        try {
            BigDecimal distanciaDriverAProveedor = googleMapsService.calcularDistancia(
                    driver.getLatitud(), driver.getLongitud(),
                    proveedor.getLatitud(), proveedor.getLongitud());

            BigDecimal distanciaProveedorACliente = googleMapsService.calcularDistancia(
                    proveedor.getLatitud(), proveedor.getLongitud(),
                    Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud()));

            return distanciaDriverAProveedor.add(distanciaProveedorACliente);
        } catch (Exception e) {
            throw new ServiceException("Error al calcular la distancia entre las entidades.", e);
        }
    }

    private BigDecimal calcularCostoPorDistancia(BigDecimal distanciaTotal, Driver driver) {
        try {
            BigDecimal precioCombustible = driver.getIdTipoCombustible().getPrecio();
            BigDecimal rendimientoGalon = driver.getRendimientoGalon();
            BigDecimal costoPorGalon = precioCombustible.divide(rendimientoGalon, BigDecimal.ROUND_HALF_UP);
            BigDecimal costoDistancia = costoPorGalon.multiply(distanciaTotal);

            return costoDistancia.add(driver.getCostoActivacion());
        } catch (Exception e) {
            throw new ServiceException("Error al calcular el costo por distancia.", e);
        }
    }

    private Pedido obtenerPedidoPorId(Integer pedidoId) {
        return pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new ServiceException("Pedido no encontrado"));
    }

    private Proveedores obtenerProveedorPorProducto(Integer productoId) {
        ProveedorProducto proveedorProducto = proveedorProductoRepo.findByIdProductoId(productoId)
                .orElseThrow(() -> new ServiceException("Producto no encontrado en ningún proveedor"));
        return proveedorProducto.getIdProveedor();
    }

    private Driver obtenerDriverPorId(Integer driverId) {
        return driverRepo.findById(driverId)
                .orElseThrow(() -> new ServiceException("Conductor no encontrado"));
    }
}
