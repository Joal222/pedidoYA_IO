package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.Solver.*;
import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsController {

    @Autowired
    private IOrderDetailsService orderDetailsService;

    @Autowired
    private IGoogleMapsService googleMapsService;

    @Autowired
    private IRutaOptimaSolver rutaOptimaSolver;

    @Autowired
    private IPedido pedidoService;

    @Autowired
    private CrudServiceProcessingController<Proveedores, Integer> proveedorService;

    @Autowired
    private CrudServiceProcessingController<ProveedorProducto, Integer> proveedorProductoService;

    @Autowired
    private CrudServiceProcessingController<Estado, Integer> estadoService;

    @Autowired
    private CrudServiceProcessingController<Driver, Integer> driverService;

    @PostMapping("/calculate-cost")
    public ResponseEntity<OrderCostResponse> calcularCostoPedido(@RequestBody OrderCostRequest orderCostRequest) {
        try {
            BigDecimal costoTotal = orderDetailsService.calcularCostoPedido(
                    orderCostRequest.getPedidoId(),
                    orderCostRequest.getProductoId(),
                    orderCostRequest.getDriverId()
            );

            BigDecimal distanciaEnKm = orderDetailsService.obtenerDistanciaEnKm(
                    orderCostRequest.getPedidoId(),
                    orderCostRequest.getProductoId(),
                    orderCostRequest.getDriverId()
            );

            OrderCostResponse response = new OrderCostResponse();
            response.setDistance(distanciaEnKm);
            response.setTotalCost(costoTotal);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/calculate-optimal-routes")
    public ResponseEntity<?> calcularRutasOptimas(@RequestBody OptimalRouteRequest request) {
        try {
            List<Pedido> pedidos = pedidoService.findAll();
            List<Proveedores> proveedores = proveedorService.findAll();

            if (pedidos.isEmpty() || proveedores.isEmpty()) {
                throw new IllegalArgumentException("No hay pedidos o proveedores disponibles.");
            }

            List<Pedido> pedidosProducto = pedidos.stream()
                    .filter(pedido -> pedido.getPedidoProductoList().stream()
                            .anyMatch(pedidoProducto -> pedidoProducto.getIdProducto().getId().equals(request.getProductoId())))
                    .collect(Collectors.toList());

            if (pedidosProducto.isEmpty()) {
                throw new IllegalArgumentException("No se encontró demanda para el producto solicitado.");
            }

            int[] demanda = orderDetailsService.obtenerDemanda(pedidosProducto, request.getProductoId());
            int[] oferta = orderDetailsService.obtenerOferta(proveedores, request.getProductoId());

            if (demanda.length == 0 || oferta.length == 0) {
                throw new IllegalArgumentException("No se encontró demanda u oferta para el producto solicitado.");
            }

            BigDecimal[][] costos = orderDetailsService.obtenerCostos(pedidosProducto, proveedores, request.getProductoId());

            List<Driver> conductoresAsignados = orderDetailsService.asignarDrivers(pedidosProducto, proveedores, request.getProductoId());

            if (conductoresAsignados.isEmpty()) {
                throw new IllegalArgumentException("No se pudo asignar conductores para los pedidos.");
            }

            OptimalRouteResponse optimalRouteResponse = rutaOptimaSolver.resolverRutas(demanda, oferta, costos);

            // Verificar si el solver devolvió asignaciones o no
            if (optimalRouteResponse == null || optimalRouteResponse.getAsignaciones() == null || optimalRouteResponse.getAsignaciones().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar una solución óptima.");
            }

            optimalRouteResponse.getAsignaciones().forEach(asignacion -> {
                asignacion.setPedidoId(asignacion.getPedidoId() + 1);
                asignacion.setProveedorId(asignacion.getProveedorId() + 1);
            });

            // Actualizar el estado de los pedidos y conductores si hay asignaciones
            if (!optimalRouteResponse.getAsignaciones().isEmpty()) {
                pedidosProducto.forEach(pedido -> {
                    pedido.setIdEstado(estadoService.findById(2)); // ID 2 = "En ruta"
                    pedidoService.save(pedido);
                });

                conductoresAsignados.forEach(driver -> {
                    driver.setIdEstado(estadoService.findById(4)); // ID 4 = "Asignado - no disponible"
                    driverService.save(driver);
                });
            }

            List<DriverDtoSolver> conductoresAsignadosDto = conductoresAsignados.stream()
                    .map(driver -> DriverDtoSolver.builder()
                            .driverId(driver.getId())
                            .marca(driver.getMarca())
                            .modeloVehiculo(driver.getModelo())
                            .limiteCapacidadKg(driver.getLimiteCapacidadKg())
                            .limiteCapacidadM3(driver.getLimiteCapacidadM3())
                            .costoActivacion(driver.getCostoActivacion())
                            .build())
                    .collect(Collectors.toList());

            optimalRouteResponse.setConductoresAsignados(conductoresAsignadosDto);

            return ResponseEntity.ok(optimalRouteResponse);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Registrar el error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }

}
