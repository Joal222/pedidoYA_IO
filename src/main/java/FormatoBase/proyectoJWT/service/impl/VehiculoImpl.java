package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Vehiculo;
import FormatoBase.proyectoJWT.model.repository.VehiculoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoImpl implements CrudServiceProcessingController<Vehiculo, Integer> {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Transactional
    @Override
    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    @Override
    public Vehiculo update(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional(readOnly = true)
    @Override
    public Vehiculo findById(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vehiculo> findAll() {
        return null;
    }

    @Transactional
    @Override
    public Vehiculo findByNombre(String nombre) {
        return null;
    }

    @Transactional
    @Override
    public void delete(Vehiculo entity) {

    }
}
