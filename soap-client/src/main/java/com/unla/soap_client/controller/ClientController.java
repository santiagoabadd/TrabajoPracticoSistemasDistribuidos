package com.unla.soap_client.controller;


import com.unla.soap_client.client.SoapClient;
import com.unla.soap_client.service1.GetOrdenComprasAgrupadasResponse;
import com.unla.soap_client.service1.GetOrdenComprasByTiendaRequest;
import com.unla.soap_client.service1.GetOrdenComprasByTiendaResponse;
import com.unla.soap_client.service1.GetOrdenComprasResponse;
import com.unla.soap_client.service2.*;
import com.unla.soap_client.service3.FiltroSoap;
import com.unla.soap_client.service3.ObtenerFiltrosResponse;
import com.unla.soap_client.service3.PostFiltroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController {


    private final SoapClient soapClientService1;
    private final SoapClient soapClientService2;
    private final SoapClient soapClientService3;

    @Autowired
    public ClientController(@Qualifier("soapClientService1") SoapClient soapClientService1,
                            @Qualifier("soapClientService2") SoapClient soapClientService2,
                            @Qualifier("soapClientService3") SoapClient soapClientService3) {
        this.soapClientService1 = soapClientService1;
        this.soapClientService2 = soapClientService2;
        this.soapClientService3 = soapClientService3;
    }


    @GetMapping("/getAllOrders")
    public GetOrdenComprasAgrupadasResponse getAllOrders() {

        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = soapClientService1.getOrdenComprasAgrupadasResponse();


        return getOrdenComprasAgrupadasResponse;
    }

    @GetMapping("/getAllOrdersByTienda/{codigoTienda}")
    public GetOrdenComprasByTiendaResponse getAllOrdersByTienda(@PathVariable String codigoTienda) {
        GetOrdenComprasByTiendaRequest getOrdenComprasByTiendaRequest=new GetOrdenComprasByTiendaRequest();
        getOrdenComprasByTiendaRequest.setCodigoTienda(codigoTienda);
        GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse = soapClientService1.getOrdenComprasByTiendaResponse(getOrdenComprasByTiendaRequest);


        return getOrdenComprasByTiendaResponse;
    }

    @GetMapping("/getAllCatalogos")
    public GetCatalogoResponse getAllCatalogos() {

        GetCatalogoResponse getCatalogoResponse = soapClientService2.getCatalogoResponse();


        return getCatalogoResponse;
    }

    @PostMapping("/postCatalogo")
    public PostCatalogoResponse postCatalogoResponse(@RequestBody CatalogoSoap catalogoSoap) {

        PostCatalogoResponse postCatalogoResponse = soapClientService2.postCatalogoResponse(catalogoSoap);

        return postCatalogoResponse;
    }

    @PostMapping("/addProductsToCatalogo")
    public AddProductsToCatalogoResponse addProductsToCatalogoResponse (@RequestBody CatalogoSoap catalogoSoap) {

        AddProductsToCatalogoResponse addProductsToCatalogoResponse = soapClientService2.addProductsToCatalogoResponse(catalogoSoap);


        return addProductsToCatalogoResponse;
    }

    @GetMapping("/PrintCatalogoPdf/{idCatalogo}")
    public GetCatalogoPdfResponse printCatalogoPdf (@PathVariable Long idCatalogo) {
        GetCatalogoPdfRequest getCatalogoPdfRequest=new GetCatalogoPdfRequest();
        getCatalogoPdfRequest.setCatalogoId(idCatalogo);
        GetCatalogoPdfResponse getCatalogoPdfResponse = soapClientService2.getCatalogoPdfResponse(getCatalogoPdfRequest);

        return getCatalogoPdfResponse;
    }

    @GetMapping("/getAllFiltros")
    public ObtenerFiltrosResponse obtenerFiltrosResponse() {

        ObtenerFiltrosResponse obtenerFiltrosResponse = soapClientService3.obtenerFiltrosResponse();


        return obtenerFiltrosResponse;
    }

    @PostMapping("/addFiltro")
    public PostFiltroResponse postFiltroResponse (@RequestBody FiltroSoap filtroSoap) {

        PostFiltroResponse postFiltroResponse = soapClientService3.postFiltroResponse(filtroSoap);


        return postFiltroResponse;
    }



}
