package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.RutaRecoleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RutaRecoleccionRepository extends JpaRepository<RutaRecoleccion, Long>, JpaSpecificationExecutor<RutaRecoleccion> {

}