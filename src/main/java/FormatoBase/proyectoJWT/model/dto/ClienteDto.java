package FormatoBase.proyectoJWT.model.dto;

import FormatoBase.proyectoJWT.model.dto.AuthAndRegister.validationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

  @NotNull(groups = validationGroups.OnCreate.class, message = "El Telefono del cliente no puede ser nulo")
  @Pattern(regexp = "\\d{8}", groups = {validationGroups.OnCreate.class, validationGroups.OnUpdate.class}, message = "El Telefono del cliente debe tener exactamente 8 dígitos")
  private String telefono;

  @Pattern(regexp = "\\d{8}|", groups = {validationGroups.OnCreate.class, validationGroups.OnUpdate.class}, message = "El Telefono de emergencia debe ser de 8 dígitos o puede estar vacío")
  private String telefonoEmergencia;

  @NotNull(groups = validationGroups.OnCreate.class, message = "El Id del cliente no puede ser nulo")
  private Integer idUser;

  @NotNull(groups = validationGroups.OnUpdate.class, message = "El Nombre del cliente no puede ser nulo para actualizar")
  private String firstName;

  @NotNull(groups = validationGroups.OnUpdate.class, message = "El Apellido del cliente no puede ser nulo para actualizar")
  private String lastName;}
