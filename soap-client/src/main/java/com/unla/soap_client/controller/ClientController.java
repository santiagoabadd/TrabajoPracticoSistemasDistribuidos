package com.unla.soap_client.controller;


import com.unla.soap_client.client.SoapClient;
import com.unla.soap_client.service1.*;
import com.unla.soap_client.service2.*;
import com.unla.soap_client.service3.FiltroSoap;
import com.unla.soap_client.service3.ObtenerFiltrosRequest;
import com.unla.soap_client.service3.ObtenerFiltrosResponse;
import com.unla.soap_client.service3.PostFiltroResponse;
import com.unla.soap_client.service4.UserBulkUploadRequest;
import com.unla.soap_client.service4.UserBulkUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class ClientController {


    private final SoapClient soapClientService1;
    private final SoapClient soapClientService2;
    private final SoapClient soapClientService3;
    private final SoapClient soapClientService4;

    @Autowired
    public ClientController(@Qualifier("soapClientService1") SoapClient soapClientService1,
                            @Qualifier("soapClientService2") SoapClient soapClientService2,
                            @Qualifier("soapClientService3") SoapClient soapClientService3,
                            @Qualifier("soapClientService4") SoapClient soapClientService4)
    {
        this.soapClientService1 = soapClientService1;
        this.soapClientService2 = soapClientService2;
        this.soapClientService3 = soapClientService3;
        this.soapClientService4 = soapClientService4;
    }


    @PostMapping("/getAllOrders")
    public GetOrdenComprasAgrupadasResponse getAllOrders(@RequestBody GetOrdenComprasRequest getOrdenComprasRequest) {

        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = soapClientService1.getOrdenComprasAgrupadasResponse(getOrdenComprasRequest);


        return getOrdenComprasAgrupadasResponse;
    }

    @GetMapping("/getAllOrdersByTienda/{codigoTienda}")
    public GetOrdenComprasByTiendaResponse getAllOrdersByTienda(@PathVariable String codigoTienda) {
        GetOrdenComprasByTiendaRequest getOrdenComprasByTiendaRequest=new GetOrdenComprasByTiendaRequest();
        getOrdenComprasByTiendaRequest.setCodigoTienda(codigoTienda);
        GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse = soapClientService1.getOrdenComprasByTiendaResponse(getOrdenComprasByTiendaRequest);


        return getOrdenComprasByTiendaResponse;
    }

    @PostMapping("/getAllCatalogos")
    public GetCatalogoResponse getAllCatalogos(@RequestBody GetCatalogoRequest getCatalogoRequest){

        GetCatalogoResponse getCatalogoResponse = soapClientService2.getCatalogoResponse(getCatalogoRequest);


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

    @PostMapping("/getAllFiltros")
    public ObtenerFiltrosResponse obtenerFiltrosResponse(@RequestBody ObtenerFiltrosRequest obtenerFiltrosRequest) {

        ObtenerFiltrosResponse obtenerFiltrosResponse = soapClientService3.obtenerFiltrosResponse(obtenerFiltrosRequest);


        return obtenerFiltrosResponse;
    }

    @PostMapping("/addFiltro")
    public PostFiltroResponse postFiltroResponse (@RequestBody FiltroSoap filtroSoap) {

        PostFiltroResponse postFiltroResponse = soapClientService3.postFiltroResponse(filtroSoap);


        return postFiltroResponse;
    }

    @PostMapping("/addUsersCsv")
    public UserBulkUploadResponse userBulkUploadResponse (@RequestBody UserBulkUploadRequest userBulkUploadRequest) {

        UserBulkUploadResponse userBulkUploadResponse = soapClientService4.userBulkUploadResponse(userBulkUploadRequest);


        return userBulkUploadResponse;
    }

    @DeleteMapping("/eliminarCatalogo/{id}")
    public EliminarCatalogoResponse eliminarCatalogoResponse (@PathVariable Long id) {

        EliminarCatalogoRequest eliminarCatalogoRequest=new EliminarCatalogoRequest();
        eliminarCatalogoRequest.setIdCatalogo(id);
        EliminarCatalogoResponse eliminarCatalogoResponse = soapClientService2.eliminarCatalogoResponse(eliminarCatalogoRequest);


        return eliminarCatalogoResponse;
    }




}
