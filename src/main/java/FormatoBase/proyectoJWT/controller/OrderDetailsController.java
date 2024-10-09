package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.OrderCostRequest;
import FormatoBase.proyectoJWT.model.dto.OrderCostResponse;
import FormatoBase.proyectoJWT.service.IGoogleMapsService;
import FormatoBase.proyectoJWT.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsController {

    @Autowired
    private IOrderDetailsService orderDetailsService;

    @Autowired
    private IGoogleMapsService googleMapsService;

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
}
