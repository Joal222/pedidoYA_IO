package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Vehiculos;
import FormatoBase.proyectoJWT.model.repository.VehiculosRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class VehiculosImpl implements CrudServiceProcessingController<Vehiculos,Integer> {

    private VehiculosRepository vehiculosRepository;

    @Transactional
    @Override
    public Vehiculos save(Vehiculos vehiculos) {
        return vehiculosRepository.save(vehiculos);
    }

    @Transactional
    @Override
    public Vehiculos update(Vehiculos vehiculos) {
        return vehiculosRepository.save(vehiculos);
    }

    @Transactional(readOnly = true)
    @Override
    public Vehiculos findById(Integer id) {
        return vehiculosRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vehiculos> findAll() {
        return (List<Vehiculos>) vehiculosRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Vehiculos findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Vehiculos vehiculos) {
        vehiculosRepository.delete(vehiculos);
    }
}
