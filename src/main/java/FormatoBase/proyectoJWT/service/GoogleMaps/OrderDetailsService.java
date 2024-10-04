package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.model.repository.DriverRepository;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.model.repository.TipoCombustibleRepository;
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
    private TipoCombustibleRepository tipoCombustibleRepo;

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private DriverRepository driverRepo;

    public Integer calcularCostoPedido(Integer pedidoId, Integer productoId, Integer tipoCombustibleId, Integer driverId) { // Método para calcular el costo total del pedido
        Pedido pedido = obtenerPedidoPorId(pedidoId);// Obtener el pedido por ID
        Proveedores proveedor = obtenerProveedorPorProducto(productoId); // Obtener el proveedor asociado al producto
        Integer distanciaEnKm = googleMapsService.calcularDistancia( // Calcular la distancia entre el proveedor y el cliente
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
                //pedido.getLatitud(), pedido.getLongitud()
        );

        BigDecimal precioCombustible = obtenerPrecioCombustible(tipoCombustibleId);  // Obtener el precio del combustible y el costo de activación del conductor
        BigDecimal costoActivacion = obtenerCostoActivacion(driverId);

        BigDecimal costoTotal = calcularCostoTotal(distanciaEnKm, precioCombustible, costoActivacion);
        return costoTotal.intValue();// Calcular el costo total
    }

    public Integer obtenerDistanciaEnKm(Integer pedidoId, Integer productoId) {// Método para obtener la distancia entre el proveedor y el cliente
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);

        return googleMapsService.calcularDistancia(
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
        );
    }

    public Pedido obtenerPedidoPorId(Integer pedidoId) {// Lógica auxiliar
        return pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public Proveedores obtenerProveedorPorProducto(Integer productoId) {
        ProveedorProducto proveedorProducto = proveedorProductoRepo.findByIdProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en ningún proveedor"));
        return proveedorProducto.getIdProveedor();
    }

    public BigDecimal obtenerPrecioCombustible(Integer tipoCombustibleId) {
        TipoCombustible tipoCombustible = tipoCombustibleRepo.findById(tipoCombustibleId)
                .orElseThrow(() -> new RuntimeException("Tipo de combustible no encontrado"));
        return tipoCombustible.getPrecio();  // Asumiendo que tienes un campo 'precio' en la entidad TipoCombustible
    }

    public BigDecimal obtenerCostoActivacion(Integer driverId) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
        return driver.getCostoActivacion();  // Asumiendo que tienes un campo 'costoActivacion' en la entidad Driver
    }

    public BigDecimal calcularCostoTotal(Integer distancia, BigDecimal precioCombustible, BigDecimal costoActivacion) {
        BigDecimal distanciaBigDecimal = BigDecimal.valueOf(distancia);
        BigDecimal costoDistancia = precioCombustible.multiply(distanciaBigDecimal);
        return costoDistancia.add(costoActivacion);
    }
}
