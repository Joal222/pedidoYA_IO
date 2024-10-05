package FormatoBase.proyectoJWT.service.impl;


import FormatoBase.proyectoJWT.model.entity.ClienteProducto;

import FormatoBase.proyectoJWT.model.repository.ClienteProductoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteProductoImpl implements CrudServiceProcessingController<ClienteProducto,Integer>{

    @Autowired
    private ClienteProductoRepository clienteProductoRepo;

    @Transactional
    @Override
    public ClienteProducto save(ClienteProducto clienteProducto) {
        return clienteProductoRepo.save(clienteProducto);
    }

    @Transactional
    @Override
    public ClienteProducto update(ClienteProducto clienteProducto) {
        return clienteProductoRepo.save(clienteProducto);
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteProducto findById(Integer id) {
        return clienteProductoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClienteProducto> findAll() {
        return null;
    }

    @Transactional
    @Override
    public void delete(ClienteProducto clienteProducto) {
         clienteProductoRepo.delete(clienteProducto);
    }
}
