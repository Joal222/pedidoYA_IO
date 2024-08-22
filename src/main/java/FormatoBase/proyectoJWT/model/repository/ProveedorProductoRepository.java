package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto, Integer>{

}