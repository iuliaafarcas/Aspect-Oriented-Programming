package com.example.a1;

import com.example.a1.controller.DeliveryController;
import com.example.a1.ui.ApplicationUi;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<ApplicationUi.StageReadyEvent> {
    public StageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    private final FxWeaver fxWeaver;

    @Override
    public void onApplicationEvent(ApplicationUi.StageReadyEvent event) {
        Stage stage = event.getStage();
        stage.setScene(new Scene(fxWeaver.loadView(DeliveryController.class), 800, 600));
        stage.setTitle("AOP project");
        stage.show();
    }

}
