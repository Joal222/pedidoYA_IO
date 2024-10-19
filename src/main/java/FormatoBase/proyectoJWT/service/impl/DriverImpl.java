package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Driver;
import FormatoBase.proyectoJWT.model.entity.Estado;
import FormatoBase.proyectoJWT.model.repository.DriverRepository;
import FormatoBase.proyectoJWT.model.repository.EstadoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverImpl implements CrudServiceProcessingController<Driver, Integer> {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CrudServiceProcessingController<Estado, Integer> estadoService;

    @Transactional
    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Transactional
    @Override
    public Driver update(Driver driver) {
        return driverRepository.save(driver);
    }

    @Transactional(readOnly = true)
    @Override
    public Driver findById(Integer id) {
        return driverRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Transactional
    @Override
    public void delete(Driver driver) {
        driverRepository.delete(driver);
    }

    public List<Driver> findByEstado(Integer estadoId) {
        Estado estado = estadoService.findById(estadoId); // Asegúrate de que existe el estado
        if (estado == null) {
            throw new IllegalArgumentException("Estado no encontrado");
        }
        return driverRepository.findByIdEstado(estado);
    }
}
