package FormatoBase.proyectoJWT.service;

import java.util.List;

public interface CrudServiceProcessingController<T,ID>{
    T save(T entity);
    T update(T entity);
    T findById(ID id);
    List<T> findAll();
    void delete(T entity);
    List<T> findByEstado(ID i);
}