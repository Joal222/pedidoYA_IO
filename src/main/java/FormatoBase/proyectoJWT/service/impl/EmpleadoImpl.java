package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Empleado;
import FormatoBase.proyectoJWT.model.repository.EmpleadoRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmpleadoImpl implements CrudServiceProcessingController<Empleado,Integer> {

    private EmpleadoRepository empleadoRepository;

    @Transactional
    @Override
    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Transactional
    @Override
    public Empleado update(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Transactional(readOnly = true)
    @Override
    public Empleado findById(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Empleado> findAll() {
        return (List<Empleado>) empleadoRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Empleado findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }
}
