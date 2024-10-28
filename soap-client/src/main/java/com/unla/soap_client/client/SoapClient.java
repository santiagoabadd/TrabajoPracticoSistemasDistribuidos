package com.unla.soap_client.client;

import com.unla.soap_client.service1.*;
import com.unla.soap_client.service2.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SoapClient extends WebServiceGatewaySupport {



    public GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse() {

        GetOrdenComprasRequest getOrdenComprasRequest=new GetOrdenComprasRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getOrdenComprasRequest");

        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = (GetOrdenComprasAgrupadasResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8081/ws", getOrdenComprasRequest, soapActionCallback);

        return getOrdenComprasAgrupadasResponse;
    }

    public GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse() {

        GetOrdenComprasByTiendaRequest getOrdenComprasByTiendaRequest=new GetOrdenComprasByTiendaRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getOrdenComprasByTiendaRequest");

        GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse = (GetOrdenComprasByTiendaResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8083/ws", getOrdenComprasByTiendaRequest, soapActionCallback);

        return getOrdenComprasByTiendaResponse;
    }



    public GetCatalogoResponse getCatalogoResponse() {

        GetCatalogoRequest getCatalogoRequest=new GetCatalogoRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getCatalogoRequest");

        GetCatalogoResponse getCatalogoResponse = (GetCatalogoResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8083/ws", getCatalogoRequest, soapActionCallback);

        return getCatalogoResponse;
    }

    public AddProductsToCatalogoResponse addProductsToCatalogoResponse() {

        AddProductsToCatalogoRequest addProductsToCatalogoRequest = new AddProductsToCatalogoRequest();


        SoapActionCallback soapActionCallback = new SoapActionCallback("http://tempuri.org/Add");

        AddProductsToCatalogoResponse addResponse = (AddProductsToCatalogoResponse) getWebServiceTemplate().marshalSendAndReceive("http://www.dneonline.com/calculator.asmx", addProductsToCatalogoRequest, soapActionCallback);

        return addResponse;
    }
}