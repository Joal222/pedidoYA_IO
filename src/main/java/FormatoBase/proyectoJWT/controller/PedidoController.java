package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.PedidoDto;
import FormatoBase.proyectoJWT.model.dto.PedidoProductoDto;
import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import FormatoBase.proyectoJWT.service.IClientes;
import FormatoBase.proyectoJWT.service.IPedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private IPedido pedidoService;

    @Autowired
    private CrudServiceProcessingController<Estado, Integer> estadoService;


    @Autowired
    private CrudServiceProcessingController<Productos, Integer> productoService;

    @Autowired
    private IClientes clientesService;


    // Método para obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.findAll();
        List<PedidoDto> pedidoDtos = pedidos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pedidoDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedidoById(@PathVariable Integer id){
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PedidoDto pedidoDto = convertToDto(pedido);
        return new ResponseEntity<>(pedidoDto,HttpStatus.OK);
    }

    private PedidoDto convertToDto(Pedido pedido) {
        return PedidoDto.builder()
                .id(pedido.getId())
                .idClientes(pedido.getIdClientes().getId())
                .idEstado(pedido.getIdEstado().getId())
                .clientePedido(pedido.getClientePedido())
                .telefonoPedido(pedido.getTelefonoPedido())
                .direccionEntrega(pedido.getDireccionEntrega())
                .latitud(pedido.getLatitud())
                .longitud(pedido.getLongitud())
                .fechaCreacion(pedido.getFechaCreacion())
                .pedidoProductoList(pedido.getPedidoProductoList() != null ?
                        pedido.getPedidoProductoList().stream()
                                .map(pedidoProducto -> PedidoProductoDto.builder()
                                        .id(pedidoProducto.getId())
                                        .idProducto(pedidoProducto.getIdProducto().getId())
                                        .cantidad(pedidoProducto.getCantidad())
                                        .build())
                                .collect(Collectors.toList())
                        : null)
                .build();
    }


    @PostMapping
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoDto pedidoDto) {
        try {
            // Validar si el cliente existe
            Clientes cliente = clientesService.findById(pedidoDto.getIdClientes());
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
            }

            // Crear la entidad Pedido
            Pedido pedido = new Pedido();
            pedido.setIdClientes(cliente);
            pedido.setIdEstado(estadoService.findById(pedidoDto.getIdEstado()));

            // Asignar direcciones y ubicaciones
            pedido.setDireccionEntrega(pedidoDto.getDireccionEntrega());
            pedido.setLatitud(pedidoDto.getLatitud());
            pedido.setLongitud(pedidoDto.getLongitud());

            // Asignar los productos relacionados
            if (pedidoDto.getPedidoProductoList() != null) {
                pedidoDto.getPedidoProductoList().forEach(pedidoProductoDto -> {
                    PedidoProducto pedidoProducto = new PedidoProducto();
                    pedidoProducto.setCantidad(pedidoProductoDto.getCantidad());
                    pedidoProducto.setIdProducto(productoService.findById(pedidoProductoDto.getIdProducto()));
                    pedidoProducto.setIdPedido(pedido);
                    pedido.getPedidoProductoList().add(pedidoProducto);
                });
            }

            // Guardar el pedido en la base de datos
            pedidoService.save(pedido);

            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido registrado con éxito");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }
}
