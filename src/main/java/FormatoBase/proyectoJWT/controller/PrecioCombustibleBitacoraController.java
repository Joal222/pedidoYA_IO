package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.PrecioCombustibleBitacoraDto;
import FormatoBase.proyectoJWT.model.entity.PrecioCombustibleBitacora;
import FormatoBase.proyectoJWT.model.entity.TipoCombustible;
import FormatoBase.proyectoJWT.model.repository.PrecioCombustibleBitacoraRepository;
import FormatoBase.proyectoJWT.model.repository.TipoCombustibleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bitacoraCombustibles")
@CrossOrigin(origins = "*")
public class PrecioCombustibleBitacoraController {

  @Autowired
  private PrecioCombustibleBitacoraRepository bitacoraRepository;

  @Autowired
  private TipoCombustibleRepository tipoCombustibleRepository;

  @GetMapping
  public ResponseEntity<List<PrecioCombustibleBitacoraDto>> getAllBitacoras() {
    List<PrecioCombustibleBitacora> bitacoras = bitacoraRepository.findAll();

    // Convertimos los registros de la bitácora a DTOs
    List<PrecioCombustibleBitacoraDto> bitacoraDtos = bitacoras.stream().map(bitacora -> {
      // Obtener el tipo de combustible basado en el ID
      TipoCombustible tipoCombustible = tipoCombustibleRepository.findById(bitacora.getIdTipoCombustible())
              .orElse(null);

      return PrecioCombustibleBitacoraDto.builder()
              .nombreTipoCombustible(tipoCombustible != null ? tipoCombustible.getNombre() : "Desconocido")
              .precioAnterior(bitacora.getPrecioAnterior())
              .fechaActualizacion(bitacora.getFechaActualizacion())
              .build();
    }).collect(Collectors.toList());

    return new ResponseEntity<>(bitacoraDtos, HttpStatus.OK);
  }
}
