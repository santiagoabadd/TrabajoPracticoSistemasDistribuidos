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

        System.out.println(request.getIdUsuario());
        System.out.println(request.getIdUsuario());

        List<FiltroSoap> filtroSoap=filtroConverter.convertFiltroToFiltroSoap(filtroRepository.findByIdUsuario(request.getIdUsuario()));


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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modificarFiltroRequest")
    @ResponsePayload
    public ModificarFiltroResponse modificarFiltro(@RequestPayload ModificarFiltroRequest request) {
        ModificarFiltroResponse response = new ModificarFiltroResponse();

        Filtro filtro = filtroConverter.convertFiltroSoapToFiltro(request.getFiltro());
        filtro = filtroRepository.save(filtro);

        FiltroSoap filtroSoap = filtroConverter.convertFiltroToFiltroSoap(filtro);
        response.setFiltro(filtroSoap);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarFiltroRequest")
    @ResponsePayload
    public EliminarFiltroResponse eliminarFiltro(@RequestPayload EliminarFiltroRequest request) {
        EliminarFiltroResponse response = new EliminarFiltroResponse();

        try {
            filtroRepository.deleteById(request.getId());
            response.setResultado("Filtro eliminado con éxito");
        } catch (Exception e) {
            response.setResultado("Error al eliminar el filtro: " + e.getMessage());
        }

        return response;
    }






}
