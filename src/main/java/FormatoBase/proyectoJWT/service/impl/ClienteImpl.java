package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.repository.ClientesRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteImpl implements CrudServiceProcessingController<Clientes,Integer>{

    private ClientesRepository clientesRepository;
    @Transactional
    @Override
    public Clientes save(Clientes clientes) {
        return clientesRepository.save(clientes);
    }

    @Transactional
    @Override
    public Clientes update(Clientes clientes) {
        return clientesRepository.save(clientes);
    }

    @Transactional(readOnly = true)
    @Override
    public Clientes findById(Integer id) {
        return clientesRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Clientes> findAll() {
        return (List<Clientes>) clientesRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Clientes findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Clientes clientes) {
        clientesRepository.delete(clientes);
    }
}
