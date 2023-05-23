package com.example.a1.aspects;

import com.example.a1.model.Delivery;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Aspect
@Component
@Order(3)
public class CachingAspect {
    private static final Logger logger = Logger.getLogger(CachingAspect.class.getName());
    private List<Delivery> cache = new ArrayList<>();

    @Pointcut("execution(public * com.example.a1.service.DeliveryService.getList()) && !within(CachingAspect)")
    private void getListOperation() {
    }


    @Pointcut("execution(public * com.example.a1.repository.DeliveryRepository.findById(..)) && !within(CachingAspect) && args(id)")
    private void getObjectByIdOperation(Long id) {
    }


    @Pointcut("execution(public * com.example.a1.repository.DeliveryRepository.save(..)) && !within(CachingAspect)")
    private void saveOperation() {
    }

    ;

    @Pointcut("execution(public * com.example.a1.repository.DeliveryRepository.deleteById(..)) && !within(CachingAspect) && args(id)")
    private void deleteByIdOperation(Long id) {
    }

    ;

    @Around("getListOperation()")
    public Object getListOperationCache(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!cache.isEmpty()) {
            logger.info("Cache retrieved");
            return cache;
        }
        Object returnObject = joinPoint.proceed();
        cache = new ArrayList<>((List<Delivery>) returnObject);
        logger.info("Objects cached");
        return returnObject;
    }


    @Around("getObjectByIdOperation(id)")
    public Object getObjectByIdOperationCache(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        Optional<Delivery> deliveryOptional = findDeliveryInCache(id);
        if (deliveryOptional.isPresent()) {
            logger.info("Object returned from cache");
            return deliveryOptional;
        } else {
            Object returnObject = joinPoint.proceed();
            Optional<Delivery> returnOptional = (Optional<Delivery>) returnObject;
            if (returnOptional.isPresent()) {
                cache.add(returnOptional.get());
                logger.info("Object cached");
            }
            return returnObject;
        }
    }

    @Around("saveOperation()")
    public Object saveOperationCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnObject = joinPoint.proceed();
        Delivery deliveryReturnObject = (Delivery) returnObject;
        Optional<Delivery> deliveryWithIdInCache = findDeliveryInCache(deliveryReturnObject.getId());
        if (deliveryWithIdInCache.isPresent()) {
            logger.info("Object cashed saved");
            cache.remove(deliveryWithIdInCache.get());
        } else {
            logger.info("Object cached");
        }
        cache.add(deliveryReturnObject);
        return returnObject;

    }

    @Around("deleteByIdOperation(id)")
    public Object deleteByIdOperationCache(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        Object returnObject = joinPoint.proceed();
        findDeliveryInCache(id).ifPresent(delivery -> cache.remove(delivery));
        logger.info("Object removed from cache");
        return returnObject;
    }


    private Optional<Delivery> findDeliveryInCache(Long id) {
        return cache.stream().filter(delivery -> id.equals(delivery.getId())).findFirst();
    }


}
