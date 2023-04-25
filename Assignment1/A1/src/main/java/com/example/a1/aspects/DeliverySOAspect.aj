package com.example.a1.aspects;

import com.example.a1.controller.DeliveryController;
import com.example.a1.model.DeliverySA;

import java.util.ArrayList;
import java.util.List;

public aspect DeliverySOAspect {
    declare parents:DeliverySA implements Observable;
    declare parents:DeliveryController implements Observer;

    pointcut stateChanges(Observable observable): @annotation(com.example.a1.annotations.StateChanger) && target(observable);
    private final List<Observer> observerList = new ArrayList<>();
    after(Observable observable): stateChanges(observable){
        for (Observer observer : getObserverList()) {
            observer.update(observable);
        }
    }

    public void subscribe(Observer observer) {
        observerList.add(observer);
    }

    private List<Observer> getObserverList() {
        return observerList;
    }

    public void DeliveryController.update(Observable observable) {
        handleSecretCode((DeliverySA) observable);
    }
}

