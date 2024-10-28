package com.unla.soap_client.client;

import com.unla.soap_client.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SoapClient extends WebServiceGatewaySupport {


    public GetOrdenComprasAgrupadasResponse GetOrdenComprasResponse() {

        GetOrdenComprasRequest getOrdenComprasRequest=new GetOrdenComprasRequest();

        SoapActionCallback soapActionCallback = new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/getOrdenComprasRequest");

        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = (GetOrdenComprasAgrupadasResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8081/ws", getOrdenComprasRequest, soapActionCallback);

        return getOrdenComprasAgrupadasResponse;
    }


}