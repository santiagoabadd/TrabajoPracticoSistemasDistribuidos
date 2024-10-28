package com.unla.soap_client.controller;


import com.unla.soap_client.client.SoapClient;
import com.unla.soap_client.wsdl.GetOrdenComprasAgrupadasResponse;
import com.unla.soap_client.wsdl.GetOrdenComprasResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController {

    @Autowired
    private SoapClient soapClient;

    @PostMapping("/getAllOrders")
    public GetOrdenComprasAgrupadasResponse getAllOrders() {

        GetOrdenComprasAgrupadasResponse getOrdenComprasResponse = soapClient.GetOrdenComprasResponse();


        return getOrdenComprasResponse;
    }

}
