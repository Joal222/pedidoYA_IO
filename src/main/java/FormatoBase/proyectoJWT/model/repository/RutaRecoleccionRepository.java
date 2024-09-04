package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.RutaRecoleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RutaRecoleccionRepository extends JpaRepository<RutaRecoleccion, Integer>{
  Optional<RutaRecoleccion> findByNombre(String nombre);
}