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
            Integer costoTotal = orderDetailsService.calcularCostoPedido( // Llamada al servicio para calcular el costo total del pedido
                    orderCostRequest.getPedidoId(),
                    orderCostRequest.getProductoId(),
                    orderCostRequest.getFuelTypeId(),
                    orderCostRequest.getDriverId()
            );

            Integer distanciaEnKm = orderDetailsService.obtenerDistanciaEnKm(orderCostRequest.getPedidoId(), orderCostRequest.getProductoId());// Obtener la distancia en kilómetros calculada por la API de Google Maps

            OrderCostResponse response = new OrderCostResponse();// Crear la respuesta con la distancia y el costo total
            response.setDistance(distanciaEnKm);
            response.setTotalCost(costoTotal);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
