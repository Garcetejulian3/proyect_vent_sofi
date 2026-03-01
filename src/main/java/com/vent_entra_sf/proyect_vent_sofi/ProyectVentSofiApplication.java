package com.vent_entra_sf.proyect_vent_sofi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProyectVentSofiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectVentSofiApplication.class, args);
	}

}
