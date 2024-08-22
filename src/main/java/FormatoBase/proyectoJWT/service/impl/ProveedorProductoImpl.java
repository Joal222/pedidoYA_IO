package FormatoBase.proyectoJWT.service.impl;
import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProveedorProductoImpl implements CrudServiceProcessingController<ProveedorProducto,Integer> {
    private ProveedorProductoRepository proveedorProductoRepository;

    @Transactional
    @Override
    public ProveedorProducto save(ProveedorProducto proveedorProducto) {
        return proveedorProductoRepository.save(proveedorProducto);
    }

    @Transactional
    @Override
    public ProveedorProducto update(ProveedorProducto proveedorProducto) {
        return proveedorProductoRepository.save(proveedorProducto);
    }

    @Transactional(readOnly = true)
    @Override
    public ProveedorProducto findById(Integer id) {
        return proveedorProductoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProveedorProducto> findAll() {
        return (List<ProveedorProducto>) proveedorProductoRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public ProveedorProducto findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(ProveedorProducto proveedorProducto) {
        proveedorProductoRepository.delete(proveedorProducto);
    }
}
