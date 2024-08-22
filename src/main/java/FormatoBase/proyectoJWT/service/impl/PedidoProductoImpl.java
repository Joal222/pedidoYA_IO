package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.PedidoProducto;
import FormatoBase.proyectoJWT.model.repository.PedidoProductoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PedidoProductoImpl implements CrudServiceProcessingController<PedidoProducto,Integer> {

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
        return (List<PedidoProducto>) pedidoProductoRepo.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public PedidoProducto findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(PedidoProducto pedidoProducto) {
        pedidoProductoRepo.delete(pedidoProducto);
    }
}
