package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.repository.UsersDao;
import FormatoBase.proyectoJWT.model.entity.AuthAndRegister.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import FormatoBase.proyectoJWT.service.IUsers;

import java.util.List;

@Service
public class UsersImpl implements IUsers {

    @Autowired
    private UsersDao usersDao;

    @Override
    public User save(User user) {
        return usersDao.save(user);
    }

    @Override
    public User update(User user) {
        return usersDao.save(user);
    }

    @Override
    public User findById(Integer id) {
        return usersDao.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) usersDao.findAll();
    }

    @Override
    public void delete(User user) {
    }
}
