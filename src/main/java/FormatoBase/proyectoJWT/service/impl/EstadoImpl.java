package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Estado;
import FormatoBase.proyectoJWT.model.repository.EstadoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoImpl implements CrudServiceProcessingController<Estado, Integer> {

    @Autowired
    private EstadoRepository estadoRepo;

    @Transactional
    @Override
    public Estado save(Estado estado) {
        return estadoRepo.save(estado);
    }

    @Transactional
    @Override
    public Estado update(Estado estado) {
        return estadoRepo.save(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public Estado findById(Integer id) {
        return estadoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Estado> findAll() {
        return null;
    }

    @Transactional
    @Override
    public Estado findByNombre(String estado) {
        return null;
    }

    @Transactional
    @Override
    public void delete(Estado estado) {
        estadoRepo.delete(estado);
    }
}
