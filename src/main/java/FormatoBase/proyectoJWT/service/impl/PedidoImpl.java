package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Pedido;
import FormatoBase.proyectoJWT.model.repository.PedidoRepository;
import FormatoBase.proyectoJWT.service.IPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoImpl implements IPedido {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Transactional
    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepo.save(pedido);
    }

    @Transactional
    @Override
    public Pedido update(Pedido pedido) {
        return pedidoRepo.save(pedido);
    }

    @Transactional(readOnly = true)
    @Override
    public Pedido findById(Integer id) {
        return pedidoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pedido> findAll() {
        return (List<Pedido>)pedidoRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(Pedido pedido) {
        pedidoRepo.delete(pedido);
    }
}
