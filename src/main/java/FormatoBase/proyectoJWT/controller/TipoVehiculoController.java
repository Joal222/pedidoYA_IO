package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.TipoVehiculoDto;
import FormatoBase.proyectoJWT.model.entity.TipoVehiculo;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipoVehiculos")
@CrossOrigin(origins = "*")
public class TipoVehiculoController {
  @Autowired
  private CrudServiceProcessingController<TipoVehiculo, Integer> tipoVehiculoService;

  @GetMapping
  public ResponseEntity<List<TipoVehiculoDto>> getAllTipoVehiculos() {
    List<TipoVehiculo> tipoVehiculos = tipoVehiculoService.findAll();
    List<TipoVehiculoDto> tipoVehiculoDtos = tipoVehiculos.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(tipoVehiculoDtos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getTipoVehiculo(@PathVariable Integer id) {
    TipoVehiculo tipoVehiculo = tipoVehiculoService.findById(id);
    if (tipoVehiculo == null) {
      return new ResponseEntity<>("Tipo de Vehículo no encontrado", HttpStatus.NOT_FOUND);
    }
    TipoVehiculoDto tipoVehiculoDto = convertToDto(tipoVehiculo);
    return new ResponseEntity<>(tipoVehiculoDto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TipoVehiculoDto> createTipoVehiculo(@RequestBody TipoVehiculoDto tipoVehiculoDto) {
    TipoVehiculo tipoVehiculo = convertToEntity(tipoVehiculoDto);
    TipoVehiculo createdTipoVehiculo = tipoVehiculoService.save(tipoVehiculo);
    return new ResponseEntity<>(convertToDto(createdTipoVehiculo), HttpStatus.CREATED);
  }

  private TipoVehiculoDto convertToDto(TipoVehiculo tipoVehiculo) {
    return TipoVehiculoDto.builder()
            .id(tipoVehiculo.getId())
            .nombre(tipoVehiculo.getNombre())
            .build();
  }

  private TipoVehiculo convertToEntity(TipoVehiculoDto tipoVehiculoDto) {
    return TipoVehiculo.builder()
            .id(tipoVehiculoDto.getId())
            .nombre(tipoVehiculoDto.getNombre())
            .build();
  }
}
