package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.TipoCombustible;
import FormatoBase.proyectoJWT.model.repository.TipoCombustibleRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoCombustibleImpl implements CrudServiceProcessingController<TipoCombustible, Integer> {

    @Autowired
    private TipoCombustibleRepository tipoCombustibleRepo;

    @Transactional
    @Override
    public TipoCombustible save(TipoCombustible tipoCombustible) {
        return tipoCombustibleRepo.save(tipoCombustible);
    }

    @Transactional
    @Override
    public TipoCombustible update(TipoCombustible tipoCombustible) {
        return tipoCombustibleRepo.save(tipoCombustible);
    }

    @Transactional(readOnly = true)
    @Override
    public TipoCombustible findById(Integer id) {
        return tipoCombustibleRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TipoCombustible> findAll() {
        return tipoCombustibleRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(TipoCombustible tipoCombustible) {
        tipoCombustibleRepo.delete(tipoCombustible);
    }
}
