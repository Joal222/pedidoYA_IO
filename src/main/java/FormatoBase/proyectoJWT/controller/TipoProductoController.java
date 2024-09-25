package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.CrearTipoProductoDto;
import FormatoBase.proyectoJWT.model.dto.ProductoDto;
import FormatoBase.proyectoJWT.model.dto.TipoProductoCantidadDto;
import FormatoBase.proyectoJWT.model.dto.TipoProductoResponseDto;
import FormatoBase.proyectoJWT.model.entity.TipoProducto;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipoProducto")
@CrossOrigin(origins = "*")
public class TipoProductoController {
  @Autowired
  private CrudServiceProcessingController<TipoProducto, Integer> tipoProductoService;


  @GetMapping ("/allTipoProductos")
  public ResponseEntity<List<TipoProductoResponseDto>> getAllPedidos() {
    List<TipoProducto> tipoProducto = tipoProductoService.findAll();
    List<TipoProductoResponseDto> tipoProductoResponseDto = tipoProducto.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(tipoProductoResponseDto, HttpStatus.OK);
  }


  @GetMapping("/{id}")
  public ResponseEntity<TipoProductoResponseDto> getPedidoById(@PathVariable Integer id){
    TipoProducto tipoProducto = tipoProductoService.findById(id);
    if (tipoProducto == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    TipoProductoResponseDto tipoProductoResponseDto = convertToDto(tipoProducto);
    return new ResponseEntity<>(tipoProductoResponseDto, HttpStatus.OK);
  }

  @GetMapping("/cantidadProductos/byTipoProducto")
  public ResponseEntity<List<TipoProductoCantidadDto>> getCantidadProductosByTipoProducto() {
    List<TipoProducto> tipoProductoList = tipoProductoService.findAll();
    if (tipoProductoList == null || tipoProductoList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    List<TipoProductoCantidadDto> tipoProductoCantidadDtos = tipoProductoList.stream()
            .map(this::getListaTipoProductos)  // Utiliza el método que crea DTOs con la cantidad de productos
            .collect(Collectors.toList());
    return new ResponseEntity<>(tipoProductoCantidadDtos, HttpStatus.OK);
  }

  // Método para mapear TipoProducto a TipoProductoCantidadDto
  private TipoProductoCantidadDto getListaTipoProductos(TipoProducto tipoProducto) {
    return TipoProductoCantidadDto.builder()
            .id(tipoProducto.getId())
            .nombre(tipoProducto.getNombre())
            .estado(tipoProducto.getEstado())
            .cantidadProductos(tipoProducto.getProductosList() != null ? tipoProducto.getProductosList().size() : 0)
            .build();
  }
  private TipoProductoResponseDto convertToDto(TipoProducto tipoProducto) {
    return TipoProductoResponseDto.builder() // Cambiado a TipoProductoResponseDto.builder()
            .id(tipoProducto.getId())
            .nombre(tipoProducto.getNombre())
            .estado(tipoProducto.getEstado())
            .productoDtoList(tipoProducto.getProductosList() != null ?
                    tipoProducto.getProductosList().stream()
                            .map(productos -> ProductoDto.builder()
                                    .id(productos.getId())
                                    .nombreProducto(productos.getNombreProducto())
                                    .descripcion(productos.getDescripcion())
                                    .dimensionM3(productos.getDimensionM3())
                                    .precio(productos.getPrecio())
                                    .pesoKg(productos.getPesoKg())
                                    .url(productos.getUrl())
                                    .build())
                            .collect(Collectors.toList())
                    : List.of())
            .build(); // Cerrar correctamente el builder
  }
  @PostMapping("/create")
  public ResponseEntity<?> createTipoProducto(@Valid @RequestBody CrearTipoProductoDto tipoProductoDto) {
    try {
      TipoProducto tipoProducto = new TipoProducto();
      tipoProducto.setNombre(tipoProductoDto.getNombre());
      tipoProducto.setEstado(tipoProductoDto.getEstado());

      // Guardar en la base de datos
      tipoProductoService.save(tipoProducto);
      return new ResponseEntity<>("TipoProducto creado exitosamente", HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<>("Error al crear el TipoProducto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateTipoProducto(@PathVariable Integer id, @Valid @RequestBody CrearTipoProductoDto tipoProductoDto) {
    try {
      TipoProducto existingTipoProducto = tipoProductoService.findById(id);
      if (existingTipoProducto == null) {
        return new ResponseEntity<>("TipoProducto no encontrado", HttpStatus.NOT_FOUND);
      }

      // Actualizar los campos nombre y estado
      existingTipoProducto.setNombre(tipoProductoDto.getNombre());
      existingTipoProducto.setEstado(tipoProductoDto.getEstado());

      // Guardar cambios en la base de datos
      tipoProductoService.save(existingTipoProducto);
      return new ResponseEntity<>("TipoProducto actualizado exitosamente", HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>("Error al actualizar el TipoProducto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
