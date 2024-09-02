package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.PedidoDto;
import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.service.IClientes;
import FormatoBase.proyectoJWT.service.IPedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private IPedido pedidoService;

    @Autowired
    private IClientes clientesService;

    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoDto pedidoDto){

        try {
            Pedido pedido = new Pedido();

            pedido.setIdClientes(clientesService.findById(pedidoDto.getIdClientes()));
            Pedido nuevoPedido = pedidoService.save(pedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }
}
