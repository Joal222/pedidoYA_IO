package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.ClienteProducto;
import FormatoBase.proyectoJWT.model.entity.Empleado;
import FormatoBase.proyectoJWT.model.repository.EmpleadoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoImpl implements CrudServiceProcessingController<Empleado, Integer> {

    @Autowired
    private EmpleadoRepository empleadoRepo;
    @Transactional
    @Override
    public Empleado save(Empleado empleado) {
        return empleadoRepo.save(empleado);
    }

    @Transactional
    @Override
    public Empleado update(Empleado empleado) {
        return empleadoRepo.save(empleado);
    }

    @Transactional(readOnly = true)
    @Override
    public Empleado findById(Integer id) {
        return empleadoRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Empleado> findAll() {
        return empleadoRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(Empleado empleado) {
        empleadoRepo.delete(empleado);
    }

    @Override
    public List<Empleado> findByEstado(Integer i) {
        return List.of();
    }
}
