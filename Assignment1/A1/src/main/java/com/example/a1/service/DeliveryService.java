package com.example.a1.service;

import com.example.a1.model.Delivery;
import com.example.a1.model.DeliverySA;
import com.example.a1.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeliveryService {
    private final String SECRET_CODE = "isthisagoodsecretcode?";
    private final DeliverySA deliverySA;
    private final DeliveryRepository repository;

    @Autowired
    public DeliveryService(DeliverySA deliverySA, DeliveryRepository repository) {
        this.deliverySA = deliverySA;
        this.repository = repository;
    }


    public Delivery addDelivery(Delivery item) {

        Delivery newDelivery = this.repository.save(item);
        if (SECRET_CODE.equals(newDelivery.getName())) {
            deliverySA.setActivated(true);
        }
        return newDelivery;
    }

    public void deleteDelivery(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Delivery with id " + id + " does NOT exist!");
        }
        var oldDelivery = repository.findById(id);
        repository.deleteById(id);
        if (SECRET_CODE.equals(oldDelivery.get().getName())) {
            deliverySA.setActivated(false);
        }
    }

    @Transactional
    public Delivery updateDelivery(Delivery item) {
        Delivery newItem = this.repository.findById(item.getId()).orElseThrow(() -> new RuntimeException("Game with id " + item.getId() + " not found"));
        String oldName=newItem.getName();
        newItem.setName(item.getName());
        newItem.setQuantity(item.getQuantity());
        newItem.setPrice(item.getPrice());
        if(newItem.getName().equals(SECRET_CODE)){
            deliverySA.setActivated(true);
        } else if (oldName.equals(SECRET_CODE))  deliverySA.setActivated(false);
        return repository.save(newItem);
    }

    public List<Delivery> getList() {
        List<Delivery> list_ = this.repository.findAll();
        deliverySA.setActivated(list_.stream().anyMatch(delivery -> delivery.getName().equals(SECRET_CODE)));
        return list_;
    }

}
