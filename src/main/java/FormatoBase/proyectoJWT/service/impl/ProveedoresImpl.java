package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Clientes;
import FormatoBase.proyectoJWT.model.entity.Proveedores;
import FormatoBase.proyectoJWT.model.repository.ProveedoresRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProveedoresImpl implements CrudServiceProcessingController<Proveedores,Integer> {
    private ProveedoresRepository proveedoresRepository;

    @Transactional
    @Override
    public Proveedores save(Proveedores proveedores) {
        return proveedoresRepository.save(proveedores);
    }

    @Transactional
    @Override
    public Proveedores update(Proveedores proveedores) {
        return proveedoresRepository.save(proveedores);
    }

    @Transactional(readOnly = true)
    @Override
    public Proveedores findById(Integer id) {
        return proveedoresRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proveedores> findAll() {
        return (List<Proveedores>) proveedoresRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Proveedores findByNombre(String nombre) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public void delete(Proveedores proveedores) {
        proveedoresRepository.delete(proveedores);
    }
}
