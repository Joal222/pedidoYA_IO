package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Integer>{
  Optional<Estado> findByNombre(String nombre);

}