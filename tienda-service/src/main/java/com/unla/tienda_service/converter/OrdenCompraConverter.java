package com.unla.tienda_service.converter;

import com.unla.tienda_service.model.ItemOrden;
import com.unla.tienda_service.model.OrdenCompra;
import io.spring.guides.gs_producing_web_service.ItemOrdenSoap;
import io.spring.guides.gs_producing_web_service.OrdenCompraSoap;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class OrdenCompraConverter {

    public OrdenCompra convertOrdenCompraSoapToOrdenCompra(OrdenCompraSoap ordenCompraSoap) {
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setId(ordenCompraSoap.getId());
        ordenCompra.setCodigoTienda(ordenCompraSoap.getCodigoTienda());
        ordenCompra.setObservaciones(ordenCompraSoap.getObservaciones());
        ordenCompra.setEstado(convertToModelEstadoOrden(ordenCompraSoap.getEstado()));
        ordenCompra.setFechaRecepcion(
                Optional.ofNullable(ordenCompraSoap.getFechaRecepcion())
                        .map(fecha -> convertToLocalDate(fecha.toGregorianCalendar()))
                        .orElse(null)
        );
        ordenCompra.setFechaSolicitud(convertToLocalDate(ordenCompraSoap.getFechaSolicitud().toGregorianCalendar()));
        ordenCompra.setItems(this.mapItemOrden(ordenCompraSoap.getItems()));
        ordenCompra.setIdOrdenDespacho(ordenCompra.getIdOrdenDespacho());
        return ordenCompra;
    }

    public OrdenCompraSoap convertOrdenCompraToOrdenCompraSoap(OrdenCompra ordenCompra) {
        OrdenCompraSoap ordenCompraSoap = new OrdenCompraSoap();
        ordenCompraSoap.setId(ordenCompra.getId());
        ordenCompraSoap.setCodigoTienda(ordenCompra.getCodigoTienda());
        ordenCompraSoap.setObservaciones(ordenCompra.getObservaciones());
        ordenCompraSoap.setEstado(convertToSoapEstadoOrden(ordenCompra.getEstado()));
ordenCompraSoap.setFechaRecepcion(convertToXMLGregorianCalendar(ordenCompra.getFechaRecepcion()));
        ordenCompraSoap.setFechaSolicitud(convertToXMLGregorianCalendar(ordenCompra.getFechaSolicitud()));
        List<ItemOrdenSoap> items = ordenCompraSoap.getItems();
        items.clear();
        items.addAll(this.mapItemOrdenSoap(ordenCompra.getItems()));
        ordenCompraSoap.setIdOrdenDespacho(ordenCompra.getIdOrdenDespacho());
        return ordenCompraSoap;
    }

    public List<OrdenCompra> convertOrdenCompraSoapToOrdenCompra(List<OrdenCompraSoap> ordenCompraSoaps) {
        List<OrdenCompra> productModels = new ArrayList<OrdenCompra>();
        for (OrdenCompraSoap ordenCompraSoap : ordenCompraSoaps) {
            productModels.add(convertOrdenCompraSoapToOrdenCompra(ordenCompraSoap));
        }
        return productModels;
    }

    public List<OrdenCompraSoap> convertOrdenCompraToOrdenCompraSoapList(List<OrdenCompra> ordenCompraList) {
        ordenCompraList.forEach(oc -> Hibernate.initialize(oc.getItems()));
        List<OrdenCompraSoap> ordenCompraSoapList = new ArrayList<OrdenCompraSoap>();
        for (OrdenCompra ordenCompra : ordenCompraList) {
            ordenCompraSoapList.add(convertOrdenCompraToOrdenCompraSoap(ordenCompra));
        }
        return ordenCompraSoapList;
    }

    private static LocalDate convertToLocalDate(GregorianCalendar calendar) {
        Instant instant = calendar.toZonedDateTime().toInstant();
        return instant.atZone(calendar.getTimeZone().toZoneId()).toLocalDate();
    }

    public List<ItemOrden> mapItemOrden(List<ItemOrdenSoap> itemOrdenSoapList) {
        return itemOrdenSoapList.stream()
                .map(this::mapSingleItemOrden)
                .collect(Collectors.toList());
    }

    public List<ItemOrdenSoap> mapItemOrdenSoap(List<ItemOrden> itemOrdenList) {
        return itemOrdenList.stream()
                .map(this::mapSingleItemOrdenSoap)
                .collect(Collectors.toList());
    }

    private ItemOrden mapSingleItemOrden(ItemOrdenSoap itemOrdenSoap) {
        ItemOrden itemOrden = new ItemOrden();
        itemOrden.setId(itemOrdenSoap.getId());
        itemOrden.setCodigoArticulo(itemOrdenSoap.getCodigoArticulo());
        itemOrden.setCantidadSolicitada(itemOrdenSoap.getCantidadSolicitada());
        itemOrden.setTalle(itemOrdenSoap.getTalle());
        itemOrden.setColor(itemOrdenSoap.getColor());
        return itemOrden;
    }

    private ItemOrdenSoap mapSingleItemOrdenSoap(ItemOrden itemOrden) {
        ItemOrdenSoap itemOrdenSoap = new ItemOrdenSoap();
        itemOrdenSoap.setId(itemOrden.getId());
        itemOrdenSoap.setCodigoArticulo(itemOrden.getCodigoArticulo());
        itemOrdenSoap.setCantidadSolicitada(itemOrden.getCantidadSolicitada());
        itemOrdenSoap.setTalle(itemOrden.getTalle());
        itemOrdenSoap.setColor(itemOrden.getColor());
        return itemOrdenSoap;
    }

    public XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        try {
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static io.spring.guides.gs_producing_web_service.EstadoOrden convertToSoapEstadoOrden(OrdenCompra.EstadoOrden estadoOrden) {
        if (estadoOrden == null) {
            return null;
        }

        switch (estadoOrden) {
            case SOLICITADA:
                return io.spring.guides.gs_producing_web_service.EstadoOrden.SOLICITADA;
            case RECHAZADA:
                return io.spring.guides.gs_producing_web_service.EstadoOrden.RECHAZADA;
            case ACEPTADA:
                return io.spring.guides.gs_producing_web_service.EstadoOrden.ACEPTADA;
            case RECIBIDA:
                return io.spring.guides.gs_producing_web_service.EstadoOrden.RECIBIDA;
            default:
                throw new IllegalArgumentException("Estado no reconocido: " + estadoOrden);
        }
    }

    public static OrdenCompra.EstadoOrden convertToModelEstadoOrden(io.spring.guides.gs_producing_web_service.EstadoOrden soapEstadoOrden) {
        if (soapEstadoOrden == null) {
            return null;
        }

        switch (soapEstadoOrden) {
            case SOLICITADA:
                return OrdenCompra.EstadoOrden.SOLICITADA;
            case RECHAZADA:
                return OrdenCompra.EstadoOrden.RECHAZADA;
            case ACEPTADA:
                return OrdenCompra.EstadoOrden.ACEPTADA;
            case RECIBIDA:
                return OrdenCompra.EstadoOrden.RECIBIDA;
            default:
                throw new IllegalArgumentException("Estado no reconocido: " + soapEstadoOrden);
        }
    }
}
