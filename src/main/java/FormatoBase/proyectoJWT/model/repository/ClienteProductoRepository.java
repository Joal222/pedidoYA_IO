package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.ClienteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClienteProductoRepository extends JpaRepository<ClienteProducto, Long>, JpaSpecificationExecutor<ClienteProducto> {

}