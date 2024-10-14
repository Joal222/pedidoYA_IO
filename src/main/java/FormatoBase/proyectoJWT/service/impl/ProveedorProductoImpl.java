package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.ProveedorProducto;
import FormatoBase.proyectoJWT.model.repository.ProveedorProductoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorProductoImpl implements CrudServiceProcessingController<ProveedorProducto,Integer> {

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepo;

    @Transactional
    @Override
    public ProveedorProducto save(ProveedorProducto proveedorProducto) {
        return proveedorProductoRepo.save(proveedorProducto);
    }

    @Transactional
    @Override
    public ProveedorProducto update(ProveedorProducto proveedorProducto) {
        return proveedorProductoRepo.save(proveedorProducto);
    }

    @Transactional
    @Override
    public ProveedorProducto findById(Integer id) {
        return proveedorProductoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProveedorProducto> findAll() {
        return (List<ProveedorProducto>) proveedorProductoRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(ProveedorProducto proveedorProducto) {
        proveedorProductoRepo.delete(proveedorProducto);
    }
}
