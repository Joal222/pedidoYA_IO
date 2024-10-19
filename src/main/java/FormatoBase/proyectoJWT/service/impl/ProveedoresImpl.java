package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Proveedores;
import FormatoBase.proyectoJWT.model.repository.ProveedoresRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedoresImpl implements CrudServiceProcessingController<Proveedores, Integer> {

    @Autowired
    private ProveedoresRepository proveedoresRepo;
    @Transactional
    @Override
    public Proveedores save(Proveedores proveedores) {
        return proveedoresRepo.save(proveedores);
    }

    @Transactional
    @Override
    public Proveedores update(Proveedores proveedores) {
        return proveedoresRepo.save(proveedores);
    }

    @Transactional(readOnly = true)
    @Override
    public Proveedores findById(Integer id) {
        return proveedoresRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proveedores> findAll() {
        return (List<Proveedores>) proveedoresRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(Proveedores proveedores) {
        proveedoresRepo.delete(proveedores);
    }

    @Override
    public List<Proveedores> findByEstado(Integer i) {
        return List.of();
    }
}
