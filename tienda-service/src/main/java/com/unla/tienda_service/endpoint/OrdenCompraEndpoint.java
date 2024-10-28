package com.unla.tienda_service.endpoint;




import com.unla.tienda_service.converter.OrdenCompraConverter;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.repository.OrdenCompraRepository;
import com.unla.tienda_service.service.OrdenCompraServiceSoap;
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
public class OrdenCompraEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private OrdenCompraServiceSoap ordenCompraServiceSoap;


    @Autowired
    private OrdenCompraConverter ordenCompraConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdenComprasRequest")
    @ResponsePayload
    public GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse(@RequestPayload GetOrdenComprasRequest request) {
        GetOrdenComprasAgrupadasResponse response = new GetOrdenComprasAgrupadasResponse();

        String codigoArticulo = request.getCodigoArticulo();
        Date fechaDesde = request.getFechaDesde() != null ? this.convertirXMLGregorianCalendarADate(request.getFechaDesde()) : null;
        Date fechaHasta = request.getFechaHasta() != null ?  this.convertirXMLGregorianCalendarADate(request.getFechaHasta()) : null;
        System.out.println(fechaHasta);
        String estado = request.getEstado() != null ?  request.getEstado().toString() : null;
        String codigoTienda = request.getCodigoTienda();

        List<OrdenCompraSoap> ordenCompraFiltrada = ordenCompraServiceSoap.getOrdenCompras(
                codigoTienda, codigoArticulo, fechaDesde, fechaHasta, estado
        );

        List<OrdenCompra> ordenCompras=ordenCompraConverter.convertOrdenCompraSoapToOrdenCompra(ordenCompraFiltrada);

        List<OrdenCompraResumenSoap> ordenCompraAgrupada=ordenCompraServiceSoap.getOrdenComprasResumen(ordenCompras);

        response.getOrdenComprasResumen().addAll(ordenCompraAgrupada);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdenComprasByTiendaRequest")
    @ResponsePayload
    public GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse(@RequestPayload GetOrdenComprasByTiendaRequest request) {
        GetOrdenComprasByTiendaResponse response = new GetOrdenComprasByTiendaResponse();



        List<OrdenCompraSoap> ordenCompra= ordenCompraConverter.convertOrdenCompraToOrdenCompraSoapList(ordenCompraRepository.findByCodigoTienda(request.getCodigoTienda()));

        System.out.println(ordenCompra.get(1).getEstado());
        List<OrdenCompra> ordenCompras=ordenCompraConverter.convertOrdenCompraSoapToOrdenCompra(ordenCompra);
        System.out.println(ordenCompras.get(1).getEstado());
        List<OrdenCompraResumenSoap> ordenCompraAgrupada=ordenCompraServiceSoap.getOrdenComprasResumen(ordenCompras);
        System.out.println(ordenCompraAgrupada.get(1).getEstado());
        System.out.println("ashee");
        response.getOrdenComprasResumen().addAll(ordenCompraAgrupada);
        return response;
    }


    public Date convertirXMLGregorianCalendarADate(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar != null) {
            return xmlGregorianCalendar.toGregorianCalendar().getTime();
        }
        return null;
    }


}
