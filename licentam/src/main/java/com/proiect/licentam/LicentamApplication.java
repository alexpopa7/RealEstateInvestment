package com.proiect.licentam;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LicentamApplication {

	public static void main(String[] args) {


//		SpringApplication.run(LicentamApplication.class, args);
		Application.launch(JavaFxApplication.class, args);
	}

}
