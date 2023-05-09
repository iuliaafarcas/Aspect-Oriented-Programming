package com.example.a1;

import com.example.a1.ui.ApplicationUi;
import javafx.application.Application;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy
public class A1Application {

	public static void main(String[] args) {
		Application.launch(ApplicationUi.class, args);
	}

	@Bean
	public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext){
		return new SpringFxWeaver(applicationContext);
	}
}

