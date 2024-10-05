package com.unla.proveedor_service;

import com.unla.proveedor_service.models.Proveedor;
import com.unla.proveedor_service.repositories.ProductRepository;
import com.unla.proveedor_service.repositories.ProveedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProveedorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProveedorServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner run(ProveedorRepository proveedorRepository){
		return args ->{

 Proveedor proveedor =new Proveedor();
 proveedorRepository.save(proveedor);



		};

}
}
