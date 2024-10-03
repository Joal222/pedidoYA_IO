package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import FormatoBase.proyectoJWT.model.entity.Proveedores;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepo;

    @Autowired
    private GoogleMapsService googleMapsService;

    public Integer calcularCostoPedido(Integer pedidoId, Integer productoId, Integer tipoCombustibleId, Integer driverId) { // Método para calcular el costo total del pedido
        Pedido pedido = obtenerPedidoPorId(pedidoId);// Obtener el pedido por ID
        Proveedores proveedor = obtenerProveedorPorProducto(productoId); // Obtener el proveedor asociado al producto
        Integer distanciaEnKm = googleMapsService.calcularDistancia( // Calcular la distancia entre el proveedor y el cliente
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
                //pedido.getLatitud(), pedido.getLongitud()
        );

        Integer precioCombustible = obtenerPrecioCombustible(tipoCombustibleId);  // Obtener el precio del combustible y el costo de activación del conductor
        Integer costoActivacion = obtenerCostoActivacion(driverId);

        return calcularCostoTotal(distanciaEnKm, precioCombustible, costoActivacion);// Calcular el costo total
    }

    public Integer obtenerDistanciaEnKm(Integer pedidoId, Integer productoId) {// Método para obtener la distancia entre el proveedor y el cliente
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Proveedores proveedor = obtenerProveedorPorProducto(productoId);

        return googleMapsService.calcularDistancia(
                proveedor.getLatitud(), proveedor.getLongitud(),
                Double.parseDouble(pedido.getLatitud()), Double.parseDouble(pedido.getLongitud())
        );
    }

    // Lógica auxiliar
    public Pedido obtenerPedidoPorId(Integer pedidoId) {
        return pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public Proveedores obtenerProveedorPorProducto(Integer productoId) {
        ProveedorProducto proveedorProducto = proveedorProductoRepo.findByIdProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en ningún proveedor"));
        return proveedorProducto.getIdProveedor();
    }

    public Integer obtenerPrecioCombustible(Integer tipoCombustibleId) {
        return 3;// Simulación del precio del combustible
    }

    public Integer obtenerCostoActivacion(Integer driverId) {
        return 10;// Simulación del costo de activación del conductor
    }

    public Integer calcularCostoTotal(Integer distancia, Integer precioCombustible, Integer costoActivacion) {
        return distancia * precioCombustible + costoActivacion;
    }
}
