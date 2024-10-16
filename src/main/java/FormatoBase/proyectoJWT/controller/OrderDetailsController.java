package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.Solver.*;
import FormatoBase.proyectoJWT.model.entity.Driver;
import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import FormatoBase.proyectoJWT.model.entity.Proveedores;
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
    public ResponseEntity<OptimalRouteResponse> calcularRutasOptimas(@RequestBody OptimalRouteRequest request) {
        try {

            List<Pedido> pedidos = pedidoService.findAll();
            List<Proveedores> proveedores = proveedorService.findAll();

            int[] demanda = orderDetailsService.obtenerDemanda(pedidos, request.getProductoId());
            int[] oferta = orderDetailsService.obtenerOferta(proveedores, request.getProductoId());

            BigDecimal[][] costos = orderDetailsService.obtenerCostos(pedidos, proveedores, request.getProductoId());  // Puedes ajustar el driverId si es necesario

            List<Driver> conductoresAsignados = orderDetailsService.asignarDrivers(pedidos, proveedores, request.getProductoId());

            List<DriverDtoSolver> conductoresAsignadosDto = conductoresAsignados.stream()
                    .map(driver -> DriverDtoSolver.builder()
                            .driverId(driver.getId())
                            .modeloVehiculo(driver.getModelo())
                            .limiteCapacidadKg(driver.getLimiteCapacidadKg())
                            .limiteCapacidadM3(driver.getLimiteCapacidadM3())
                            .costoActivacion(driver.getCostoActivacion())
                            .build())
                    .collect(Collectors.toList());

            OptimalRouteResponse optimalRouteResponse = rutaOptimaSolver.resolverRutas(demanda, oferta, costos);

            optimalRouteResponse.setConductoresAsignados(conductoresAsignadosDto);

            return ResponseEntity.ok(optimalRouteResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
