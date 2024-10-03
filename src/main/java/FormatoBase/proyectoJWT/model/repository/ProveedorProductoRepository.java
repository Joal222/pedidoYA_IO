package FormatoBase.proyectoJWT.model.repository;

import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto, Integer>{

    Optional<ProveedorProducto> findByIdProductoId(Integer productoId);

}