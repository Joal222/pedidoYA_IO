package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.DriverDto;
import FormatoBase.proyectoJWT.model.entity.*;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

  @Autowired
  private CrudServiceProcessingController<Driver, Integer> driverService;

  @Autowired
  private CrudServiceProcessingController<TipoVehiculo, Integer> tipoVehiculoService;

  @Autowired
  private CrudServiceProcessingController<Empleado, Integer> empleadoService;

  @Autowired
  private CrudServiceProcessingController<TipoCombustible, Integer> tipoCombustibleService;

  @Autowired
  private CrudServiceProcessingController<Estado, Integer> estadoService;

  // Listar todos los drivers
  @GetMapping
  public ResponseEntity<List<DriverDto>> getAllDrivers() {
    List<Driver> drivers = driverService.findAll();
    List<DriverDto> driverDtos = drivers.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(driverDtos, HttpStatus.OK);
  }

  // Listar driver por ID
  @GetMapping("/{id}")
  public ResponseEntity<DriverDto> getDriverById(@PathVariable Integer id) {
    Driver driver = driverService.findById(id);
    if (driver == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    DriverDto driverDto = convertToDto(driver);
    return new ResponseEntity<>(driverDto, HttpStatus.OK);
  }

  // Convertir Driver a DriverDTO
  private DriverDto convertToDto(Driver driver) {
    return DriverDto.builder()
            .id(driver.getId())
            .modelo(driver.getModelo())
            .marca(driver.getMarca())
            .limiteCapacidadM3(driver.getLimiteCapacidadM3())
            .limiteCapacidadKg(driver.getLimiteCapacidadKg())
            .latitud(driver.getLatitud())
            .longitud(driver.getLongitud())
            .costoActivacion(driver.getCostoActivacion())
            .rendimientoGalon(driver.getRendimientoGalon())
            .direccion(driver.getDireccion())
            .idEstado(driver.getIdEstado().getId())
            .idTipoVehiculo(driver.getIdTipoVehiculo().getId())
            .idEmpleado(driver.getIdEmpleado().getId())
            .idTipoCombustible(driver.getIdTipoCombustible().getId())
            .build();
  }


  @PostMapping
  public ResponseEntity<?> createDriver(@Valid @RequestBody DriverDto driverDTO) {
    try {
      // Validar si el tipo de vehículo existe
      TipoVehiculo tipoVehiculo = tipoVehiculoService.findById(driverDTO.getIdTipoVehiculo());
      if (tipoVehiculo == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tipo de vehículo no existe");
      }

      // Validar si el empleado existe
      Empleado empleado = empleadoService.findById(driverDTO.getIdEmpleado());
      if (empleado == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");
      }

      // Validar si el empleado ya tiene un driver asignado
      List<Driver> driversExistentes = driverService.findAll(); // Obtener todos los drivers
      boolean empleadoConDriver = driversExistentes.stream()
              .anyMatch(driver -> driver.getIdEmpleado().getId().equals(empleado.getId()));

      if (empleadoConDriver) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El empleado ya tiene un driver asignado. No se puede asignar más de un driver por empleado.");
      }

      // Validar si el tipo de combustible existe
      TipoCombustible tipoCombustible = tipoCombustibleService.findById(driverDTO.getIdTipoCombustible());
      if (tipoCombustible == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tipo de combustible no existe");
      }

      // Crear la entidad Driver
      Driver driver = new Driver();
      driver.setModelo(driverDTO.getModelo());
      driver.setMarca(driverDTO.getMarca());
      driver.setLimiteCapacidadM3(driverDTO.getLimiteCapacidadM3());
      driver.setLimiteCapacidadKg(driverDTO.getLimiteCapacidadKg());
      driver.setLatitud(driverDTO.getLatitud());
      driver.setLongitud(driverDTO.getLongitud());
      driver.setCostoActivacion(driverDTO.getCostoActivacion());
      driver.setRendimientoGalon(driverDTO.getRendimientoGalon());
      driver.setDireccion(driverDTO.getDireccion());
      Estado estadoInicial = estadoService.findById(3);//Crear en DB estado inicial con ID 3 = "Sin asignar"
      driver.setIdEstado(estadoInicial);
      driver.setIdTipoVehiculo(tipoVehiculo);
      driver.setIdEmpleado(empleado);
      driver.setIdTipoCombustible(tipoCombustible);

      // Guardar el driver en la base de datos
      driverService.save(driver);

      return ResponseEntity.status(HttpStatus.CREATED).body("Driver registrado con éxito");

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del sistema");
    }
  }
}
