package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.ProveedorDto;
import FormatoBase.proyectoJWT.model.entity.Proveedores;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proveedor")
@CrossOrigin(origins = "*")
public class ProveedorController {

@Autowired
private CrudServiceProcessingController<Proveedores, Integer> proveedoresService;

@Autowired
private ModelMapper modelMapper;

    @PostMapping("/post/supplier")
    public ResponseEntity<?> crearProveedor(@Valid @RequestBody ProveedorDto proveedorDto) {

        try {
            Proveedores proveedor = new Proveedores();

            proveedor.setNombreComercial(proveedorDto.getNombreComercial());
            proveedor.setLatitud(proveedorDto.getLatitud());
            proveedor.setLongitud(proveedorDto.getLongitud());
            proveedor.setDireccion(proveedorDto.getDireccion());
            proveedor.setHorarioAtencion(proveedorDto.getHorarioAtencion());
            proveedor.setPbx(proveedorDto.getPbx());

            Proveedores nuevoProveedor = proveedoresService.save(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del Sistema");
        }
    }

    @GetMapping("/get/supplierAll")
    public ResponseEntity<?> listarProveedores() {
        try {
            List<Proveedores> proveedores = proveedoresService.findAll();
            if (proveedores.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay proveedores registrados");
            }

            List<ProveedorDto> proveedoresDto = proveedores.stream()
                    .map(proveedor -> modelMapper.map(proveedor, ProveedorDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(proveedoresDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del Sistema");
        }
    }

    @GetMapping("/get/supplier/{id}")
    public ResponseEntity<?> obtenerProveedorPorId(@PathVariable Integer id) {
        try {
            Proveedores proveedor = proveedoresService.findById(id);
            if (proveedor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado");
            }
            ProveedorDto proveedorDto = modelMapper.map(proveedor, ProveedorDto.class);
            return ResponseEntity.ok(proveedorDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
        }
    }
}