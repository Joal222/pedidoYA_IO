package FormatoBase.proyectoJWT.service.impl;


import FormatoBase.proyectoJWT.model.entity.Tripulacion;
import FormatoBase.proyectoJWT.model.repository.TripulacionRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TripulacionImpl implements CrudServiceProcessingController<Tripulacion,Integer> {

    private TripulacionRepository tripulacionRepository;

    @Transactional
    @Override
    public Tripulacion save(Tripulacion tripulacion) {
        return tripulacionRepository.save(tripulacion);
    }

    @Transactional
    @Override
    public Tripulacion update(Tripulacion tripulacion) {
        return tripulacionRepository.save(tripulacion);
    }

    @Transactional(readOnly = true)
    @Override
    public Tripulacion findById(Integer id) {
        return tripulacionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Tripulacion> findAll() {
        return (List<Tripulacion>) tripulacionRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Tripulacion findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Tripulacion tripulacion) {
        tripulacionRepository.delete(tripulacion);
    }
}
