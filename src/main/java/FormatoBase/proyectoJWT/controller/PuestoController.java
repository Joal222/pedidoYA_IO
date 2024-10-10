package FormatoBase.proyectoJWT.controller;


import FormatoBase.proyectoJWT.model.dto.PuestoDto;
import FormatoBase.proyectoJWT.model.entity.Puesto;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/puesto")
@CrossOrigin(origins = "*")
public class PuestoController {


  @Autowired
  private CrudServiceProcessingController<Puesto, Integer> puestoService;

  // Método para listar todos los puestos
  @GetMapping
  public ResponseEntity<List<PuestoDto>> getAllPedidos() {
    List<Puesto> puestos = puestoService.findAll();
    List<PuestoDto> puestoDtos = puestos.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(puestoDtos, HttpStatus.OK);
  }

  private PuestoDto convertToDto(Puesto puesto) {
    return PuestoDto.builder()
            .id(puesto.getId())
            .descripcion(puesto.getDescripcion())
            .build();
  }
}
