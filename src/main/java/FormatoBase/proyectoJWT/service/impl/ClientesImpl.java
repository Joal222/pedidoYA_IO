package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.repository.ClientesRepository;
import FormatoBase.proyectoJWT.service.IClientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientesImpl implements IClientes {

    @Autowired
    private ClientesRepository clientesRepo;

    @Transactional
    @Override
    public Clientes save(Clientes clientes) {
        return clientesRepo.save(clientes);
    }

    @Transactional
    @Override
    public Clientes update(Clientes clientes) {
        return clientesRepo.save(clientes);
    }

    @Transactional(readOnly = true)
    @Override
    public Clientes findById(Integer id) {
        return clientesRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Clientes> findAll() {
        return (List<Clientes>) clientesRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(Clientes clientes) {
        clientesRepo.delete(clientes);
    }
}
