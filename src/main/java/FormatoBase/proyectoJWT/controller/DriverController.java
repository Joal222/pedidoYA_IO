package FormatoBase.proyectoJWT.controller;

import FormatoBase.proyectoJWT.model.dto.DriverDto;
import FormatoBase.proyectoJWT.model.entity.Driver;
import FormatoBase.proyectoJWT.model.entity.Empleado;
import FormatoBase.proyectoJWT.model.entity.TipoCombustible;
import FormatoBase.proyectoJWT.model.entity.TipoVehiculo;
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
            .limiteCapacidad(driver.getLimiteCapacidad())
            .latitud(driver.getLatitud())
            .longitud(driver.getLongitud())
            .costoActivacion(driver.getCostoActivacion())
            .rendimientoGalon(driver.getRendimientoGalon())
            .direccion(driver.getDireccion())
            .idTipoVehiculo(driver.getIdTipoVehiculo().getId())
            .idEmpleado(driver.getIdEmpleado().getId())
            .idTipoCombustible(driver.getIdTipoCombustible().getId())
            .build();
  }

  // Crear un nuevo driver con validaciones de llaves foráneas
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

      // Validar si el tipo de combustible existe
      TipoCombustible tipoCombustible = tipoCombustibleService.findById(driverDTO.getIdTipoCombustible());
      if (tipoCombustible == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tipo de combustible no existe");
      }

      // Crear la entidad Driver
      Driver driver = new Driver();
      driver.setModelo(driverDTO.getModelo());
      driver.setMarca(driverDTO.getMarca());
      driver.setLimiteCapacidad(driverDTO.getLimiteCapacidad());
      driver.setLatitud(driverDTO.getLatitud());
      driver.setLongitud(driverDTO.getLongitud());
      driver.setCostoActivacion(driverDTO.getCostoActivacion());
      driver.setRendimientoGalon(driverDTO.getRendimientoGalon());
      driver.setDireccion(driverDTO.getDireccion());
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
