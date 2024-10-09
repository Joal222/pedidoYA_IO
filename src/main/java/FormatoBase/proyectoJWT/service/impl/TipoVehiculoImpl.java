package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.TipoVehiculo;
import FormatoBase.proyectoJWT.model.repository.TipoVehiculoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoVehiculoImpl implements CrudServiceProcessingController<TipoVehiculo,Integer> {


    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepo;

    @Transactional
    @Override
    public TipoVehiculo save(TipoVehiculo tipoVehiculo) {
        return tipoVehiculoRepo.save(tipoVehiculo);
    }

    @Transactional
    @Override
    public TipoVehiculo update(TipoVehiculo tipoVehiculo) {
        return tipoVehiculoRepo.save(tipoVehiculo);
    }

    @Transactional(readOnly = true)
    @Override
    public TipoVehiculo findById(Integer id) {
        return tipoVehiculoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TipoVehiculo> findAll() {
        return tipoVehiculoRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(TipoVehiculo tipoVehiculo) {
        tipoVehiculoRepo.delete(tipoVehiculo);

    }
}
