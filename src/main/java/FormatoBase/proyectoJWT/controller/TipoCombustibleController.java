package FormatoBase.proyectoJWT.controller;


import FormatoBase.proyectoJWT.model.dto.TipoCombustibleDto;
import FormatoBase.proyectoJWT.model.entity.PrecioCombustibleBitacora;
import FormatoBase.proyectoJWT.model.entity.TipoCombustible;
import FormatoBase.proyectoJWT.model.repository.PrecioCombustibleBitacoraRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipoCombustible")
@CrossOrigin(origins = "*")
public class TipoCombustibleController {
  @Autowired
  private CrudServiceProcessingController<TipoCombustible, Integer> tipoCombustibleService;

  @Autowired
  private PrecioCombustibleBitacoraRepository precioCombustibleBitacoraRepository;

  @GetMapping
  public ResponseEntity<List<TipoCombustibleDto>> getAllTipoCombustibles() {
    List<TipoCombustible> tipoCombustibles = tipoCombustibleService.findAll();
    List<TipoCombustibleDto> tipoCombustibleDtos = tipoCombustibles.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(tipoCombustibleDtos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getTipoCombustible(@PathVariable Integer id) {
    TipoCombustible tipoCombustible = tipoCombustibleService.findById(id);
    if (tipoCombustible == null) {
      return new ResponseEntity<>("Tipo de Combustible no encontrado", HttpStatus.NOT_FOUND);
    }
    TipoCombustibleDto tipoCombustibleDto = convertToDto(tipoCombustible);
    return new ResponseEntity<>(tipoCombustibleDto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TipoCombustibleDto> createTipoCombustible(@RequestBody TipoCombustibleDto tipoCombustibleDto) {
    TipoCombustible tipoCombustible = convertToEntity(tipoCombustibleDto);
    TipoCombustible createdTipoCombustible = tipoCombustibleService.save(tipoCombustible);
    return new ResponseEntity<>(convertToDto(createdTipoCombustible), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateTipoCombustible(@PathVariable Integer id, @RequestBody TipoCombustibleDto tipoCombustibleDto) {
    TipoCombustible existingTipoCombustible = tipoCombustibleService.findById(id);
    if (existingTipoCombustible == null) {
      return new ResponseEntity<>("Tipo de Combustible no encontrado", HttpStatus.NOT_FOUND);
    }

    // Guardar el precio anterior en la bitácora
    PrecioCombustibleBitacora bitacora = PrecioCombustibleBitacora.builder()
            .idTipoCombustible(existingTipoCombustible.getId())
            .precioAnterior(existingTipoCombustible.getPrecio())
            .fechaActualizacion(LocalDateTime.now())
            .build();
    precioCombustibleBitacoraRepository.save(bitacora);

    // Actualizar los valores del combustible
    existingTipoCombustible.setNombre(tipoCombustibleDto.getNombre());
    existingTipoCombustible.setPrecio(tipoCombustibleDto.getPrecio());

    TipoCombustible updatedTipoCombustible = tipoCombustibleService.save(existingTipoCombustible);
    return new ResponseEntity<>(convertToDto(updatedTipoCombustible), HttpStatus.OK);
  }

  private TipoCombustibleDto convertToDto(TipoCombustible tipoCombustible) {
    return TipoCombustibleDto.builder()
            .id(tipoCombustible.getId())
            .nombre(tipoCombustible.getNombre())
            .precio(tipoCombustible.getPrecio())
            .build();
  }

  private TipoCombustible convertToEntity(TipoCombustibleDto tipoCombustibleDto) {
    return TipoCombustible.builder()
            .id(tipoCombustibleDto.getId())
            .nombre(tipoCombustibleDto.getNombre())
            .precio(tipoCombustibleDto.getPrecio())
            .build();
  }
}

