package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Driver;
import FormatoBase.proyectoJWT.model.repository.VehiculoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoImpl implements CrudServiceProcessingController<Driver, Integer> {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Transactional
    @Override
    public Driver save(Driver driver) {
        return vehiculoRepository.save(driver);
    }

    @Transactional
    @Override
    public Driver update(Driver driver) {
        return vehiculoRepository.save(driver);
    }

    @Transactional(readOnly = true)
    @Override
    public Driver findById(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Driver> findAll() {
        return null;
    }

    @Transactional
    @Override
    public void delete(Driver driver) {
        vehiculoRepository.delete(driver);
    }
}
