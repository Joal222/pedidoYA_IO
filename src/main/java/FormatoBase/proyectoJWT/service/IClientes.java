package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.entity.Clientes;

import java.util.List;

public interface IClientes {

    Clientes save(Clientes clientes);
    Clientes update(Clientes clientes);
    Clientes findById(Integer id);
    List<Clientes> findAll();
    void delete(Clientes clientes);
}
