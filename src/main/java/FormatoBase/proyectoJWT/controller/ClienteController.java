package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.ClienteDto;
import FormatoBase.proyectoJWT.model.dto.ClienteResponseDto;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.User;
import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.service.IClientes;
import FormatoBase.proyectoJWT.service.IUsers;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.validationGroups;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

  @Autowired
  private IClientes clienteService;

  @Autowired
  private IUsers userService; // Para validar la existencia del User si es necesario


  // Método para obtener todos los clientes
  @GetMapping
  public ResponseEntity<List<ClienteResponseDto>> getAllClientes() {
    List<Clientes> clientes = clienteService.findAll();
    List<ClienteResponseDto> clienteDtos = clientes.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(clienteDtos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Integer id){
    Clientes cliente = clienteService.findById(id);
    if (cliente == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ClienteResponseDto clienteDto = convertToDto(cliente);
    return new ResponseEntity<>(clienteDto, HttpStatus.OK);
  }

  private ClienteResponseDto convertToDto(Clientes cliente) {
    User user = cliente.getIdUser();
    return ClienteResponseDto.builder()
            .id(cliente.getId())
            .telefono(cliente.getTelefono())
            .telefonoEmergencia(cliente.getTelefonoEmergencia())
            .idUser(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .build();
  }

  @PostMapping
  public ResponseEntity<?> crearCliente(@Validated(validationGroups.OnCreate.class) @RequestBody ClienteDto clienteDto) {
    try {
      // Validar si el usuario existe y obtener la entidad User
      User user = userService.findById(clienteDto.getIdUser());
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
      }

      // Crear la entidad Clientes
      Clientes cliente = new Clientes();
      cliente.setTelefono(clienteDto.getTelefono());
      cliente.setTelefonoEmergencia(clienteDto.getTelefonoEmergencia());
      cliente.setIdUser(user);

      // Guardar el cliente en la base de datos
      clienteService.save(cliente);

      return ResponseEntity.status(HttpStatus.CREATED).body("Cliente registrado con éxito");

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarCliente(@PathVariable Integer id, @Validated(validationGroups.OnUpdate.class) @RequestBody ClienteDto clienteDto) {
    try {
      Clientes cliente = clienteService.findById(id);
      if (cliente == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
      }

     User user = cliente.getIdUser();
      // Actualizar los datos del usuario
      user.setFirstName(clienteDto.getFirstName());  // Actualizar el nombre
      user.setLastName(clienteDto.getLastName());    // Actualizar el apellido


      // Actualizar la entidad Cliente
      cliente.setTelefono(clienteDto.getTelefono());
      cliente.setTelefonoEmergencia(clienteDto.getTelefonoEmergencia());


      // Guardar los cambios
      clienteService.update(cliente);

      return ResponseEntity.status(HttpStatus.OK).body("Cliente actualizado con éxito");

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
    }
  }

}

