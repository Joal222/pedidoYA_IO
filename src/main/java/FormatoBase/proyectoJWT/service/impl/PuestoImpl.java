package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Puesto;
import FormatoBase.proyectoJWT.model.repository.PuestoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PuestoImpl implements CrudServiceProcessingController<Puesto, Integer> {

    @Autowired
    private PuestoRepository puestoRepo;

    @Transactional
    @Override
    public Puesto save(Puesto puesto) {
        return puestoRepo.save(puesto);
    }

    @Transactional
    @Override
    public Puesto update(Puesto puesto) {
        return puestoRepo.save(puesto);
    }

    @Transactional(readOnly = true)
    @Override
    public Puesto findById(Integer id) {
        return puestoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Puesto> findAll() {
        return puestoRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(Puesto puesto) {
        puestoRepo.delete(puesto);
    }
}
