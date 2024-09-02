package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.entity.Pedido;

import java.util.List;

public interface IPedido {

    Pedido save(Pedido pedido);
    Pedido update(Pedido pedido);
    Pedido findById(Integer id);
    List<Pedido> findAll();
    void delete(Pedido pedido);
}
