package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.User;

import java.util.List;

public interface IUsers {
    User save(User user);
    User update(User user);
    User findById(Integer id);
    List<User> findAll();
    void delete(User user);
}
