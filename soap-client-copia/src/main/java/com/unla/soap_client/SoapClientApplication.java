package com.unla.soap_client;

import com.unla.soap_client.client.SoapClient;
import com.unla.soap_client.wsdl.GetOrdenComprasAgrupadasResponse;
import com.unla.soap_client.wsdl.GetOrdenComprasResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoapClientApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoapClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SoapClientApplication.class, args);
	}

	@Bean
	CommandLineRunner init(SoapClient soapClient){
		return	args -> {

			GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse= soapClient.GetOrdenComprasResponse();

			LOGGER.info("RESPUESTA SERVIDOR {}",getOrdenComprasAgrupadasResponse.getOrdenComprasResumen());
		};
	}
}
