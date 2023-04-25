package com.example.a1.model;

import com.example.a1.annotations.StateChanger;
import org.springframework.stereotype.Component;

@Component
public class DeliverySA {
    private boolean isActivated;

    public DeliverySA() {
        this.isActivated = false;
    }


    public boolean isActivated() {
        return isActivated;
    }

    @StateChanger
    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }
}
