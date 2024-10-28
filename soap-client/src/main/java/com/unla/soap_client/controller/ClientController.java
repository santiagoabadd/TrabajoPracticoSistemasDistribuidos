package com.unla.soap_client.controller;


import com.unla.soap_client.client.SoapClient;
import com.unla.soap_client.service1.GetOrdenComprasAgrupadasResponse;
import com.unla.soap_client.service1.GetOrdenComprasResponse;
import com.unla.soap_client.service2.GetCatalogoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController {


    private final SoapClient soapClientService1; // Corregido: Declarar las variables de cliente aquí
    private final SoapClient soapClientService2;

    @Autowired // Asegúrate de inyectar dependencias correctamente
    public ClientController(@Qualifier("soapClientService1") SoapClient soapClientService1,
                            @Qualifier("soapClientService2") SoapClient soapClientService2) {
        this.soapClientService1 = soapClientService1; // Inicializa la variable de instancia
        this.soapClientService2 = soapClientService2; // Inicializa la variable de instancia
    }


    @PostMapping("/getAllOrders")
    public GetOrdenComprasAgrupadasResponse getAllOrders() {

        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = soapClientService1.getOrdenComprasAgrupadasResponse();


        return getOrdenComprasAgrupadasResponse;
    }

    @PostMapping("/getAllCatalogos")
    public GetCatalogoResponse getAllCatalogos() {

        GetCatalogoResponse getCatalogoResponse = soapClientService2.getCatalogoResponse();


        return getCatalogoResponse;
    }


}
