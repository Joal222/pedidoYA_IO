package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Driver, Integer>{

}