package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.TipoProducto;
import FormatoBase.proyectoJWT.model.repository.TipoProductoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoProductoImpl implements CrudServiceProcessingController<TipoProducto,Integer> {

    @Autowired
    private TipoProductoRepository tipoProductoRepo;

    @Transactional
    @Override
    public TipoProducto save(TipoProducto tipoProducto) {
        return tipoProductoRepo.save(tipoProducto);
    }

    @Transactional
    @Override
    public TipoProducto update(TipoProducto tipoProducto) {
        return tipoProductoRepo.save(tipoProducto);
    }

    @Transactional(readOnly = true)
    @Override
    public TipoProducto findById(Integer id) {
        return tipoProductoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TipoProducto> findAll() {
        return null;
    }

    @Transactional
    @Override
    public void delete(TipoProducto tipoProducto) {
        tipoProductoRepo.delete(tipoProducto);
    }
}
