package FormatoBase.proyectoJWT.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

  @NotNull(message = "El Telefono del cliente no puede ser nulo")
  @Pattern(regexp = "\\d{8}", message = "El Telefono del cliente debe tener exactamente 8 dígitos")
  private String telefono;

  @Pattern(regexp = "\\d{8}|", message = "El Telefono de emergencia debe ser de 8 dígitos o puede estar vacío")
  private String telefonoEmergencia;

  @NotNull(message = "El Nombre del cliente no puede ser nulo para actualizar")
  private String firstName;

  @NotNull(message = "El Apellido del cliente no puede ser nulo para actualizar")
  private String lastName;}
