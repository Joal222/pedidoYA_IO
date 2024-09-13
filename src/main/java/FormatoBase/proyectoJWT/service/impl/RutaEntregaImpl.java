package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.RutaEntrega;
import FormatoBase.proyectoJWT.model.repository.RutaEntregaRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaEntregaImpl implements CrudServiceProcessingController<RutaEntrega,Integer> {

    @Autowired
    private RutaEntregaRepository rutaEntregaRepo;

    @Transactional
    @Override
    public RutaEntrega save(RutaEntrega rutaEntrega) {
        return rutaEntregaRepo.save(rutaEntrega);
    }

    @Transactional
    @Override
    public RutaEntrega update(RutaEntrega rutaEntrega) {
        return rutaEntregaRepo.save(rutaEntrega);
    }

    @Transactional(readOnly = true)
    @Override
    public RutaEntrega findById(Integer id) {
        return rutaEntregaRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RutaEntrega> findAll() {
        return rutaEntregaRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(RutaEntrega rutaEntrega) {
        rutaEntregaRepo.delete(rutaEntrega);

    }
}
