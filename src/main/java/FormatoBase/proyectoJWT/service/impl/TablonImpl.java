package FormatoBase.proyectoJWT.service.impl;

import FormatoBase.proyectoJWT.model.entity.OrderDetails;
import FormatoBase.proyectoJWT.model.repository.TablonRepository;
import FormatoBase.proyectoJWT.service.CrudServiceProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TablonImpl implements CrudServiceProcessingController<OrderDetails,Integer> {

    @Autowired
    private TablonRepository tablonRepo;

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
}
