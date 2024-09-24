package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IClientes {

    Clientes save(Clientes clientes);
    Clientes update(Clientes clientes);
    Clientes findById(Integer id);
    List<Clientes> findAll();
    Clientes findByUserId(Integer idUser);

    void delete(Clientes clientes);
}
