package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.RutaRecoleccion;
import FormatoBase.proyectoJWT.model.entity.Tablon;
import FormatoBase.proyectoJWT.model.repository.TablonRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TablonImpl implements CrudServiceProcessingController<Tablon,Integer> {

    @Autowired
    private TablonRepository tablonRepo;

    @Override
    public Tablon save(Tablon tablon) {
        return tablonRepo.save(tablon);
    }

    @Override
    public Tablon update(Tablon tablon) {
        return null;
    }

    @Override
    public Tablon findById(Integer integer) {
        return null;
    }

    @Override
    public List<Tablon> findAll() {
        return List.of();
    }

    @Override
    public void delete(Tablon tablon) {

    }
}
