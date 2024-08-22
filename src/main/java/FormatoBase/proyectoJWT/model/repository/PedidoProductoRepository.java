package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Integer> {

}