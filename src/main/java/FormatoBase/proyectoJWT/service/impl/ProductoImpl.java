package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.Productos;
import FormatoBase.proyectoJWT.model.repository.ProductosRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class  ProductoImpl implements CrudServiceProcessingController<Productos,Integer> {


    @Autowired
    private ProductosRepository productosRepo;

    @Transactional
    @Override
    public Productos save(Productos productos) {
        return productosRepo.save(productos);
    }

    @Transactional
    @Override
    public Productos update(Productos productos) {
        return productosRepo.save(productos);
    }

    @Transactional(readOnly = true)
    @Override
    public Productos findById(Integer id) {
        return productosRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Productos> findAll() {
        return (List<Productos>) productosRepo.findAll();
    }

    @Transactional
    @Override
    public void delete(Productos productos) {
        productosRepo.delete(productos);
    }

    @Override
    public List<Productos> findByEstado(Integer i) {
        return List.of();
    }

    // Nuevo método para obtener productos con pedidos
    @Transactional(readOnly = true)
    public List<Productos> obtenerProductosConPedidos() {
        return productosRepo.findAll().stream()
                .filter(producto -> producto.getPedidoProductoList().stream()
                        .anyMatch(pedidoProducto -> pedidoProducto.getIdPedido().getIdEstado().getId() == 1)) // Filtrar productos con pedidos en estado 1
                .collect(Collectors.toList());
    }
}
