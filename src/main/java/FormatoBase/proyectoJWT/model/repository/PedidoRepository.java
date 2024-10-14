package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.model.entity.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}