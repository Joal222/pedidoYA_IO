package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.Vehiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehiculosRepository extends JpaRepository<Vehiculos, Integer>{

}