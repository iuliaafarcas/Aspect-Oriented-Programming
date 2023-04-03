package com.example.a1.service;

import com.example.a1.Delivery;
import com.example.a1.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery addDelivery(Delivery item) {
        return this.repository.save(item);
    }

    public void deleteDelivery(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Delivery with id " + id + " does NOT exist!");
        }
        repository.deleteById(id);
    }

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
