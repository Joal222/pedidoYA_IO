package FormatoBase.proyectoJWT.controller;


import FormatoBase.proyectoJWT.model.dto.ClienteResponseDto;
import FormatoBase.proyectoJWT.model.dto.EmpleadoResponseDto;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.User;
import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Empleado;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import FormatoBase.proyectoJWT.service.IUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {
  @Autowired
  private CrudServiceProcessingController<Empleado, Integer> empleadoService;

  @Autowired
  private IUsers userService;


  @GetMapping
  public ResponseEntity<List<EmpleadoResponseDto>> getAllEmpleados() {
    List<Empleado> empleado = empleadoService.findAll();
    List<EmpleadoResponseDto> empleadoDtos = empleado.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(empleadoDtos, HttpStatus.OK);
  }
  @GetMapping("/cliente/{id}")
  public ResponseEntity<Object> getEmpleado(@PathVariable Integer id) {
    Empleado empleado = empleadoService.findById(id);
    if (empleado == null) {
      return new ResponseEntity<>("Cliente no encontrado para este User ID", HttpStatus.NOT_FOUND);
    }
    EmpleadoResponseDto empleadoDto = convertToDto(empleado);
    return new ResponseEntity<>(empleadoDto, HttpStatus.OK);
  }
  private EmpleadoResponseDto convertToDto(Empleado empleado) {
    User user = empleado.getIdUser();
    return EmpleadoResponseDto.builder()
            .id(empleado.getId())
            .direccion(empleado.getDireccion())
            .telefono(empleado.getTelefono())
            .dpi(empleado.getDpi())
            .idUser(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .build();
  }

}
