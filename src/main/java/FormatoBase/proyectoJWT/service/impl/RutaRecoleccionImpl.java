package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.RutaRecoleccion;
import FormatoBase.proyectoJWT.model.repository.RutaEntregaRepository;
import FormatoBase.proyectoJWT.model.repository.RutaRecoleccionRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaRecoleccionImpl implements CrudServiceProcessingController<RutaRecoleccion,Integer> {

    @Autowired
    private RutaRecoleccionRepository rutaRecoleccionRepo;

    @Transactional
    @Override
    public RutaRecoleccion save(RutaRecoleccion rutaRecoleccion) {
        return rutaRecoleccionRepo.save(rutaRecoleccion);
    }

    @Transactional
    @Override
    public RutaRecoleccion update(RutaRecoleccion rutaRecoleccion) {
        return rutaRecoleccionRepo.save(rutaRecoleccion);
    }

    @Transactional(readOnly = true)
    @Override
    public RutaRecoleccion findById(Integer id) {
        return rutaRecoleccionRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RutaRecoleccion> findAll() {
        return rutaRecoleccionRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(RutaRecoleccion rutaRecoleccion) {
        rutaRecoleccionRepo.delete(rutaRecoleccion);
    }
}
