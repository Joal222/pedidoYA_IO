package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Pedidos;
import FormatoBase.proyectoJWT.model.repository.PedidosRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PedidosImpl implements CrudServiceProcessingController<Pedidos, Integer> {

    private PedidosRepository pedidosRepository;

    @Transactional
    @Override
    public Pedidos save(Pedidos pedidos) {
        return pedidosRepository.save(pedidos);
    }

    @Transactional
    @Override
    public Pedidos update(Pedidos pedidos) {
        return pedidosRepository.save(pedidos);
    }

    @Transactional(readOnly = true)
    @Override
    public Pedidos findById(Integer id) {
        return pedidosRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pedidos> findAll() {
        return (List<Pedidos>) pedidosRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Pedidos findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Pedidos pedidos) {
        pedidosRepository.delete(pedidos);
    }
}
