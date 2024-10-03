package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
}
