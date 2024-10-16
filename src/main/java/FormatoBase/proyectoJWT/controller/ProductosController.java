package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.PedidoDto;
import FormatoBase.proyectoJWT.model.dto.ProductoDto;
import FormatoBase.proyectoJWT.model.dto.ProductoPedidoDto;
import FormatoBase.proyectoJWT.model.dto.ProveedorProductoDto;
import FormatoBase.proyectoJWT.model.entity.Productos;
import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import FormatoBase.proyectoJWT.model.entity.Proveedores;
import FormatoBase.proyectoJWT.model.entity.TipoProducto;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    private CrudServiceProcessingController<Productos,Integer> productosService;
    @Autowired
    private CrudServiceProcessingController<TipoProducto, Integer> tipoProductoService;
    @Autowired
    private CrudServiceProcessingController<ProveedorProducto, Integer> proveedorProductoService;
    @Autowired
    private CrudServiceProcessingController<Proveedores, Integer> proveedoresService;
    @GetMapping("/get/listProductsAll")
    public ResponseEntity<?> getAllProducts(){
        try {
            List<Productos> productos = productosService.findAll();
            if (productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos");
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }

    @GetMapping("/get/productById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            Productos producto = productosService.findById(id);
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("producto no encontrada");
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductoDto productoDTO) {
        try {
            Productos producto = new Productos();
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setNombreProducto(productoDTO.getNombreProducto());
            producto.setDimensionM3(productoDTO.getDimensionM3());
            producto.setPesoKg(productoDTO.getPesoKg());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setUrl(productoDTO.getUrl());

            // Validar que el TipoProducto exista en la base de datos
            TipoProducto tipoProducto = tipoProductoService.findById(productoDTO.getIdTipoProducto());
            if (tipoProducto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El tipo de producto ingresado no es válido. No existe en la base de datos.");
            }
            producto.setIdTipoProducto(tipoProducto);

            // Guardar el producto primero
            Productos newProduct = productosService.save(producto);

            // Asociar el producto con los proveedores y su cantidad (disponibilidad)
            if (productoDTO.getProveedores() != null) {
                for (ProveedorProductoDto proveedorDto : productoDTO.getProveedores()) {
                    Proveedores proveedor = proveedoresService.findById(proveedorDto.getIdProveedor());
                    if (proveedor != null) {
                        ProveedorProducto proveedorProducto = new ProveedorProducto();
                        proveedorProducto.setIdProducto(newProduct);
                        proveedorProducto.setIdProveedor(proveedor);
                        proveedorProducto.setDisponibilidad(proveedorDto.getDisponibilidad());
                        proveedorProductoService.save(proveedorProducto);  // Guardar la relación
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("El proveedor con ID " + proveedorDto.getIdProveedor() + " no existe.");
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Producto Creado Con éxito y asociado a proveedores");

        } catch (Exception e) {
            e.printStackTrace();  // Imprime el error en la consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el producto: " + e.getMessage());
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable Integer id, @Valid @RequestBody ProductoDto productoDTO) {
        try {
            Productos existingProduct = productosService.findById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }

            // Asignar los campos actualizados
            existingProduct.setDescripcion(productoDTO.getDescripcion());
            existingProduct.setNombreProducto(productoDTO.getNombreProducto());
            existingProduct.setDimensionM3(productoDTO.getDimensionM3());
            existingProduct.setPesoKg(productoDTO.getPesoKg());
            existingProduct.setPrecio(productoDTO.getPrecio());
            existingProduct.setUrl(productoDTO.getUrl());

            // Validar y asignar el TipoProducto
            TipoProducto tipoProducto = tipoProductoService.findById(productoDTO.getIdTipoProducto());
            if (tipoProducto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tipo de producto ingresado no es válido");
            }
            existingProduct.setIdTipoProducto(tipoProducto);

            Productos updatedProduct = productosService.update(existingProduct);
            return ResponseEntity.ok("Producto actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    // Método para obtener productos con pedidos usando DTOs
    @GetMapping("/get/listProductoPedido")
    public List<ProductoPedidoDto> obtenerProductosConPedidos() {
        return productosService.findAll().stream()
                .filter(producto -> !producto.getPedidoProductoList().isEmpty()) // Filtrar productos con pedidos
                .map(this::convertirProductoADTO) // Convertir cada producto en un ProductoConPedidosDTO
                .collect(Collectors.toList());
    }

    private ProductoPedidoDto convertirProductoADTO(Productos producto) {
        ProductoPedidoDto productoDTO = new ProductoPedidoDto();
        productoDTO.setId(producto.getId());
        productoDTO.setNombreProducto(producto.getNombreProducto());

        // Convertir la lista de pedidos asociados a un producto
        List<PedidoDto> pedidosDTO = producto.getPedidoProductoList().stream()
                .filter(pedidoProducto -> pedidoProducto.getIdPedido().getIdEstado().getId() == 1) // Filtrar pedidos en estado 1
                .map(pedidoProducto ->  {
                    PedidoDto pedidoDTO = new PedidoDto();
                    pedidoDTO.setId(pedidoProducto.getIdPedido().getId());
                    pedidoDTO.setClientePedido(pedidoProducto.getIdPedido().getClientePedido());
                    pedidoDTO.setIdEstado(pedidoProducto.getIdPedido().getIdEstado().getId());
                    pedidoDTO.setClientePedido(pedidoProducto.getIdPedido().getClientePedido());
                    pedidoDTO.setTelefonoPedido(pedidoProducto.getIdPedido().getTelefonoPedido());
                    pedidoDTO.setFechaCreacion(pedidoProducto.getIdPedido().getFechaCreacion());
                    pedidoDTO.setLatitud(pedidoProducto.getIdPedido().getLatitud());
                    pedidoDTO.setLongitud(pedidoProducto.getIdPedido().getLongitud());
                    pedidoDTO.setDireccionEntrega(pedidoProducto.getIdPedido().getDireccionEntrega());

                    return pedidoDTO;
                })
                .collect(Collectors.toList());

        productoDTO.setPedidos(pedidosDTO);
        return productoDTO;
    }
}
