package FormatoBase.proyectoJWT.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.Rol;

public interface RolDao extends JpaRepository<Rol, Integer> {
}
