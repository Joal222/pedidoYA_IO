package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.PedidoProducto;
import FormatoBase.proyectoJWT.model.repository.PedidoProductoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoProductoImpl implements CrudServiceProcessingController<PedidoProducto, Integer> {

    @Autowired
    private PedidoProductoRepository pedidoProductoRepo;

    @Transactional
    @Override
    public PedidoProducto save(PedidoProducto pedidoProducto) {
        return pedidoProductoRepo.save(pedidoProducto);
    }

    @Transactional
    @Override
    public PedidoProducto update(PedidoProducto pedidoProducto) {
        return pedidoProductoRepo.save(pedidoProducto);
    }

    @Transactional(readOnly = true)
    @Override
    public PedidoProducto findById(Integer id) {
        return pedidoProductoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PedidoProducto> findAll() {
        return null;
    }

    @Transactional
    @Override
    public void delete(PedidoProducto pedidoProducto) {
        pedidoProductoRepo.delete(pedidoProducto);
    }
}
