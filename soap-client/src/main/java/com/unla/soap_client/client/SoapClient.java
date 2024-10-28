package com.unla.soap_client.client;

import com.unla.soap_client.service1.*;
import com.unla.soap_client.service2.*;
import com.unla.soap_client.service3.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

public class SoapClient extends WebServiceGatewaySupport {



    public GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse() {

        GetOrdenComprasRequest getOrdenComprasRequest=new GetOrdenComprasRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getOrdenComprasRequest");

        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = (GetOrdenComprasAgrupadasResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8081/ws", getOrdenComprasRequest, soapActionCallback);

        return getOrdenComprasAgrupadasResponse;
    }

    public GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse(GetOrdenComprasByTiendaRequest getOrdenComprasByTiendaRequest) {


        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getOrdenComprasByTiendaRequest");

        GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse = (GetOrdenComprasByTiendaResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8081/ws", getOrdenComprasByTiendaRequest, soapActionCallback);

        return getOrdenComprasByTiendaResponse;
    }



    public GetCatalogoResponse getCatalogoResponse() {

        GetCatalogoRequest getCatalogoRequest=new GetCatalogoRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getCatalogoRequest");

        GetCatalogoResponse getCatalogoResponse = (GetCatalogoResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8083/ws", getCatalogoRequest, soapActionCallback);

        return getCatalogoResponse;
    }

    public AddProductsToCatalogoResponse addProductsToCatalogoResponse(CatalogoSoap catalogoSoap) {

        AddProductsToCatalogoRequest addProductsToCatalogoRequest= new AddProductsToCatalogoRequest();
        addProductsToCatalogoRequest.setId(catalogoSoap.getId());

        if (catalogoSoap != null && catalogoSoap.getProducts() != null) {
            addProductsToCatalogoRequest.getProducts().addAll(catalogoSoap.getProducts());
        }

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getCatalogoRequest");

        AddProductsToCatalogoResponse addResponse = (AddProductsToCatalogoResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8083/ws", addProductsToCatalogoRequest, soapActionCallback);

        return addResponse;
    }

    public PostCatalogoResponse postCatalogoResponse(CatalogoSoap catalogoSoap) {

        PostCatalogoRequest postCatalogoRequest= new PostCatalogoRequest();
        postCatalogoRequest.setCatalogo(catalogoSoap);

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getCatalogoRequest");

        PostCatalogoResponse postCatalogoResponse = (PostCatalogoResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8083/ws", postCatalogoRequest, soapActionCallback);

        return postCatalogoResponse;
    }

    public GetCatalogoPdfResponse getCatalogoPdfResponse(GetCatalogoPdfRequest getCatalogoPdfRequest) {



        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getCatalogoPdfRequest");

        GetCatalogoPdfResponse getCatalogoPdfResponse = (GetCatalogoPdfResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8083/ws", getCatalogoPdfRequest, soapActionCallback);

        return getCatalogoPdfResponse;
    }

    public ObtenerFiltrosResponse obtenerFiltrosResponse() {

        ObtenerFiltrosRequest obtenerFiltrosRequest=new ObtenerFiltrosRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/obtenerFiltrosRequest");

        ObtenerFiltrosResponse obtenerFiltrosResponse = (ObtenerFiltrosResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8084/ws", obtenerFiltrosRequest, soapActionCallback);

        return obtenerFiltrosResponse;
    }

    public PostFiltroResponse postFiltroResponse(FiltroSoap filtroSoap) {

        PostFiltroRequest postFiltroRequest= new PostFiltroRequest();
        postFiltroRequest.setFiltro(filtroSoap);

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/postFiltroRequest");

        PostFiltroResponse postFiltroResponse = (PostFiltroResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8084/ws", postFiltroRequest, soapActionCallback);

        return postFiltroResponse;
    }
}