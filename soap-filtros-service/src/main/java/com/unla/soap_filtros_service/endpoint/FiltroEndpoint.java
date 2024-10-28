package com.unla.soap_filtros_service.endpoint;




import com.unla.soap_filtros_service.converter.FiltroConverter;
import com.unla.soap_filtros_service.model.Filtro;
import com.unla.soap_filtros_service.repository.FiltroRepository;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

@Endpoint
public class FiltroEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    private FiltroRepository filtroRepository;

    @Autowired
    private FiltroConverter filtroConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerFiltrosRequest")
    @ResponsePayload
    public ObtenerFiltrosResponse obtenerFiltrosResponse(@RequestPayload ObtenerFiltrosRequest request) {
        ObtenerFiltrosResponse response = new ObtenerFiltrosResponse();



        List<FiltroSoap> filtroSoap=filtroConverter.convertFiltroToFiltroSoap(filtroRepository.findAll());


        response.getFiltro().addAll(filtroSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postFiltroRequest")
    @ResponsePayload
    public PostFiltroResponse postFiltros(@RequestPayload PostFiltroRequest request) {

        PostFiltroResponse response = new PostFiltroResponse();
        Filtro filtro = filtroConverter.convertFiltroSoapToFiltro(request.getFiltro());

        FiltroSoap filtroSoap = filtroConverter.convertFiltroToFiltroSoap(filtroRepository.save(filtro));

        response.setFiltro(filtroSoap);
        return response;
    }








}
