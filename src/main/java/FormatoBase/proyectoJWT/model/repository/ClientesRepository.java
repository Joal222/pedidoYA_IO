package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {
      Clientes findByIdUser_Id(Integer userId);  // Accede al ID del objeto User
}