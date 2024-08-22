package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.RutaEntrega;
import FormatoBase.proyectoJWT.model.repository.RutaEntregaRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RutaEntregaImpl implements CrudServiceProcessingController<RutaEntrega,Integer> {

    private RutaEntregaRepository rutaEntregaRepository;

    @Transactional
    @Override
    public RutaEntrega save(RutaEntrega rutaEntrega) {
        return rutaEntregaRepository.save(rutaEntrega);
    }

    @Transactional
    @Override
    public RutaEntrega update(RutaEntrega rutaEntrega) {
        return rutaEntregaRepository.save(rutaEntrega);
    }

    @Transactional(readOnly = true)
    @Override
    public RutaEntrega findById(Integer id) {
        return rutaEntregaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RutaEntrega> findAll() {
        return (List<RutaEntrega>) rutaEntregaRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public RutaEntrega findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(RutaEntrega rutaEntrega) {
        rutaEntregaRepository.delete(rutaEntrega);
    }
}
