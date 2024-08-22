package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Puesto;
import FormatoBase.proyectoJWT.model.repository.PuestoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PuestoImpl implements CrudServiceProcessingController<Puesto,Integer> {

    private PuestoRepository puestoRepository;

    @Transactional
    @Override
    public Puesto save(Puesto puesto) {
        return puestoRepository.save(puesto);
    }

    @Transactional
    @Override
    public Puesto update(Puesto puesto) {
        return puestoRepository.save(puesto);
    }

    @Transactional(readOnly = true)
    @Override
    public Puesto findById(Integer id) {
        return puestoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Puesto> findAll() {
        return (List<Puesto>) puestoRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Puesto findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Puesto puesto) {
        puestoRepository.delete(puesto);
    }
}
