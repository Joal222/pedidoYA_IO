package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Productos;
import FormatoBase.proyectoJWT.model.repository.ProductosRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class  ProductoImpl implements CrudServiceProcessingController<Productos,Integer> {


    @Autowired
    private ProductosRepository productosRepo;

    @Transactional
    @Override
    public Productos save(Productos productos) {
        return productosRepo.save(productos);
    }

    @Transactional
    @Override
    public Productos update(Productos productos) {
        return productosRepo.save(productos);
    }

    @Transactional(readOnly = true)
    @Override
    public Productos findById(Integer id) {
        return productosRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Productos> findAll() {
        return null;
    }

    @Transactional
    @Override
    public Productos findByNombre(String productos) {
        return null;
    }

    @Transactional
    @Override
    public void delete(Productos productos) {
        productosRepo.delete(productos);
    }
}
