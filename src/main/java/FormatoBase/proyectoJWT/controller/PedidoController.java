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
    private CrudServiceProcessingController<RutaEntrega, Integer> rutaEntregaService;

    @Autowired
    private CrudServiceProcessingController<RutaRecoleccion, Integer> rutaRecoleccionService;

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
                .direccionEntrega(pedido.getDireccionEntrega())
                .direccionRecepcion(pedido.getDireccionRecepcion())
                //.ubicacionEntrega(pedido.getUbicacionEntrega())
                //.ubicacionRecepcion(pedido.getUbicacionRecepcion())
                .pedidoProductoList(pedido.getPedidoProductoList() != null ?
                        pedido.getPedidoProductoList().stream()
                                .map(pedidoProducto -> PedidoProductoDto.builder()
                                        .id(pedidoProducto.getId())
                                        .idProducto(pedidoProducto.getIdProducto().getId())
                                        .cantidad(pedidoProducto.getCantidad())
                                        .nombreProducto(pedidoProducto.getIdProducto().getNombreProducto()) // Obtener nombre del producto
                                        .urlImagen(pedidoProducto.getIdProducto().getUrl()) // Obtener la URL de la imagen del producto
                                        .build())
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoDto pedidoDto) {
        try {
            // Crear la entidad Pedido
            Pedido pedido = new Pedido();

            // Asignar el cliente al pedido
            pedido.setIdClientes(clientesService.findById(pedidoDto.getIdClientes()));
            // Asignar direcciones y ubicaciones
            pedido.setDireccionEntrega(pedidoDto.getDireccionEntrega());
            pedido.setDireccionRecepcion(pedidoDto.getDireccionRecepcion());
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

            // Devolver mensaje personalizado en lugar del pedido completo
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido registrado con éxito");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }
}
