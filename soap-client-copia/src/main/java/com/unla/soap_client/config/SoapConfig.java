package com.unla.soap_client.config;

import com.unla.soap_client.client.SoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.unla.soap_client.wsdl");
        return marshaller;
    }

    @Bean
    public SoapClient getSoapClient(Jaxb2Marshaller marshaller) {
        SoapClient soapClient = new SoapClient();
        soapClient.setDefaultUri("http://localhost:8081/ws");
        soapClient.setMarshaller(marshaller);
        soapClient.setUnmarshaller(marshaller);

        return soapClient;
    }

}