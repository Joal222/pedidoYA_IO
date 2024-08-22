package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDao extends JpaRepository<User, Integer> {
}
