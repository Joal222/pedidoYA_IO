package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.entity.Productos;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    private CrudServiceProcessingController<Productos,Integer> productosService;

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


}
