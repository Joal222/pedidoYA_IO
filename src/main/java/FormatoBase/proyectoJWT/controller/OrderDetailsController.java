package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.Solver.OptimalRouteRequest;
import FormatoBase.proyectoJWT.model.dto.Solver.OptimalRouteResponse;
import FormatoBase.proyectoJWT.model.dto.Solver.OrderCostRequest;
import FormatoBase.proyectoJWT.model.dto.Solver.OrderCostResponse;
import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.model.entity.PedidoProducto;
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

            BigDecimal[][] costos = orderDetailsService.obtenerCostos(pedidos, proveedores, request.getProductoId(), null);  // Puedes ajustar el driverId si es necesario

            OptimalRouteResponse optimalRouteResponse = rutaOptimaSolver.resolverRutas(demanda, oferta, costos);

            return ResponseEntity.ok(optimalRouteResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
