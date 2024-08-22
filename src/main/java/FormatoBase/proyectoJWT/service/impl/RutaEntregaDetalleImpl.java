package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.RutaEntregaDetalle;
import FormatoBase.proyectoJWT.model.repository.RutaEntregaDetalleRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RutaEntregaDetalleImpl implements CrudServiceProcessingController<RutaEntregaDetalle, Integer> {

    private RutaEntregaDetalleRepository rutaEntregaDetalleRepository;

    @Transactional
    @Override
    public RutaEntregaDetalle save(RutaEntregaDetalle rutaEntregaDetalle) {
        return rutaEntregaDetalleRepository.save(rutaEntregaDetalle);
    }

    @Transactional
    @Override
    public RutaEntregaDetalle update(RutaEntregaDetalle rutaEntregaDetalle) {
        return rutaEntregaDetalleRepository.save(rutaEntregaDetalle);
    }

    @Transactional(readOnly = true)
    @Override
    public RutaEntregaDetalle findById(Integer id) {
        return rutaEntregaDetalleRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RutaEntregaDetalle> findAll() {
        return (List<RutaEntregaDetalle>) rutaEntregaDetalleRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public RutaEntregaDetalle findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(RutaEntregaDetalle rutaEntregaDetalle) {
        rutaEntregaDetalleRepository.delete(rutaEntregaDetalle);
    }
}
