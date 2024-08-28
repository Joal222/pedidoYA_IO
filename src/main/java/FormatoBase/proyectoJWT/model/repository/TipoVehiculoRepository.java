package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long>, JpaSpecificationExecutor<TipoVehiculo> {

}