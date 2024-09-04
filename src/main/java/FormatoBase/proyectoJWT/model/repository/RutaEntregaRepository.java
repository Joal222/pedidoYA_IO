package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.RutaEntrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RutaEntregaRepository extends JpaRepository<RutaEntrega, Integer>{
  Optional<RutaEntrega> findByNombre(String nombre);
}