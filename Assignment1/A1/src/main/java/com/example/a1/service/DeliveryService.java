package com.example.a1.service;

import com.example.a1.aspects.RequiresAuthentication;
import com.example.a1.model.Delivery;
import com.example.a1.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository repository;

    @Autowired
    public DeliveryService( DeliveryRepository repository) {

        this.repository = repository;
    }

    @RequiresAuthentication
    public Delivery addDelivery(Delivery item) {

        Delivery newDelivery = this.repository.save(item);
        return newDelivery;
    }

    @RequiresAuthentication
    public void deleteDelivery(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Delivery with id " + id + " does NOT exist!");
        }
        var oldDelivery = repository.findById(id);
        repository.deleteById(id);

    }

    @RequiresAuthentication
    @Transactional
    public Delivery updateDelivery(Delivery item) {
        Delivery newItem = this.repository.findById(item.getId()).orElseThrow(() -> new RuntimeException("Game with id " + item.getId() + " not found"));

        newItem.setName(item.getName());
        newItem.setQuantity(item.getQuantity());
        newItem.setPrice(item.getPrice());
        return repository.save(newItem);
    }

    public List<Delivery> getList() {
        return this.repository.findAll();
    }

}
