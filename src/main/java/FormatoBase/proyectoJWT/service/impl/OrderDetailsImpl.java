package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.OrderDetails;
import FormatoBase.proyectoJWT.model.repository.OrderDetailsRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsImpl implements CrudServiceProcessingController<OrderDetails,Integer> {

    @Autowired
    private OrderDetailsRepository tablonRepo;

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return tablonRepo.save(orderDetails);
    }

    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        return null;
    }

    @Override
    public OrderDetails findById(Integer integer) {
        return null;
    }

    @Override
    public List<OrderDetails> findAll() {
        return List.of();
    }

    @Override
    public void delete(OrderDetails orderDetails) {

    }

    @Override
    public List<OrderDetails> findByEstado(Integer i) {
        return List.of();
    }
}
