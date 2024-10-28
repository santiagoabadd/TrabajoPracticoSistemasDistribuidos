package com.unla.soap_client.config;

import com.unla.soap_client.client.SoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerService1() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.unla.soap_client.service1");
        return marshaller;
    }

    @Bean
    public Jaxb2Marshaller marshallerService2() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.unla.soap_client.service2");
        return marshaller;
    }

    @Bean
    public Jaxb2Marshaller marshallerService3() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.unla.soap_client.service3");
        return marshaller;
    }

    @Bean
    public Jaxb2Marshaller marshallerService4() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.unla.soap_client.service4");
        return marshaller;
    }

    @Bean(name = "soapClientService1")
    public SoapClient soapClientService1(Jaxb2Marshaller marshallerService1) {
        SoapClient soapClient = new SoapClient();
        soapClient.setDefaultUri("http://localhost:8081/ws");
        soapClient.setMarshaller(marshallerService1);
        soapClient.setUnmarshaller(marshallerService1);
        return soapClient;
    }

    @Bean(name = "soapClientService2")
    public SoapClient soapClientService2(Jaxb2Marshaller marshallerService2) {
        SoapClient soapClient = new SoapClient();
        soapClient.setDefaultUri("http://localhost:8083/ws");
        soapClient.setMarshaller(marshallerService2);
        soapClient.setUnmarshaller(marshallerService2);
        return soapClient;
    }

    @Bean(name = "soapClientService3")
    public SoapClient soapClientService3(Jaxb2Marshaller marshallerService3) {
        SoapClient soapClient = new SoapClient();
        soapClient.setDefaultUri("http://localhost:8084/ws");
        soapClient.setMarshaller(marshallerService3);
        soapClient.setUnmarshaller(marshallerService3);
        return soapClient;
    }

    @Bean(name = "soapClientService4")
    public SoapClient soapClientService4(Jaxb2Marshaller marshallerService4) {
        SoapClient soapClient = new SoapClient();
        soapClient.setDefaultUri("http://localhost:8080/ws");
        soapClient.setMarshaller(marshallerService4);
        soapClient.setUnmarshaller(marshallerService4);
        return soapClient;
    }



}