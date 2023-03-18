package com.example.a1.service;

import com.example.a1.Delivery;
import com.example.a1.repository.DeliveryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeliveryService {
    private DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery addDelivery(Delivery item){
        log.info(" ENTER saveDelivery(): name=\"{}\", quantity=\"{}\", price=\"{}\" ", item.getName(), item.getQuantity(), item.getPrice());
        try{
            Delivery savedDelivery= this.repository.save(item);
            log.info(" IN saveDelivery(): name=\"{}\", quantity=\"{}\", price=\"{}\" ", item.getName(), item.getQuantity(), item.getPrice());
            return item;
        } catch (Exception e){
            log.error("ERROR: saveDelivery()", e);
            throw e;
        }

    }

    public void deleteDelivery(Long id){
        log.info(" ENTER deleteDelivery() id=\"{}\"", id);
        try{
            repository.findById(id).orElseThrow(()->new RuntimeException("Delivery with id "+id+ " does NOT exist!"));
            repository.deleteById(id);
            log.info(" IN deleteDelivery() id=\"{}\"", id);
        } catch (Exception e){
            log.error(" ERROR deleteDelivery()", e);
            throw e;
        }

    }

    public Delivery updateDelivery(Delivery item){
        log.info(" ENTER updateDelivery(): name=\"{}\", quantity=\"{}\", price=\"{}\" ", item.getName(), item.getQuantity(), item.getPrice());

        try {
            Delivery newItem = this.repository.findById(item.getId()).orElseThrow(()-> new RuntimeException("Game with id "+ item.getId()+" not found"));
            newItem.setName(item.getName());
            newItem.setQuantity(item.getQuantity());
            newItem.setPrice(item.getPrice());
            log.info(" IN updateDelivery(): name=\"{}\", quantity=\"{}\", price=\"{}\" ", item.getName(), item.getQuantity(), item.getPrice());
            return newItem;
        } catch (Exception e){
            log.error("ERROR: updateDelivery()", e);
            throw e;
        }
    }
    public List<Delivery> getList(){
        log.info(" ENTER getList()");
        try{

        List<Delivery> result= this.repository.findAll();
        log.info(" IN getList()");
        return result;
        } catch (Exception e){
            log.error(" ERROR getList(): Error on read", e);
            throw e;
        }

    }

}
