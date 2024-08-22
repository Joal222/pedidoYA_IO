package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Productos;
import FormatoBase.proyectoJWT.model.repository.ProductosRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductosImpl implements CrudServiceProcessingController<Productos,Integer> {

    private ProductosRepository productosRepository;

    @Transactional
    @Override
    public Productos save(Productos productos) {
        return productosRepository.save(productos);
    }

    @Transactional
    @Override
    public Productos update(Productos productos) {
        return productosRepository.save(productos);
    }

    @Transactional(readOnly = true)
    @Override
    public Productos findById(Integer id) {
        return productosRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Productos> findAll() {
        return (List<Productos>) productosRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Productos findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Productos productos) {
        productosRepository.delete(productos);
    }
}
