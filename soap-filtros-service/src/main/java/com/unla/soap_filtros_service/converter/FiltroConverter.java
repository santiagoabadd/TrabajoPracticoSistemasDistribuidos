package com.unla.soap_filtros_service.converter;


import com.unla.soap_filtros_service.model.Filtro;
import io.spring.guides.gs_producing_web_service.FiltroSoap;
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
public class FiltroConverter {

    public Filtro convertFiltroSoapToFiltro(FiltroSoap filtroSoap) {
        Filtro filtro = new Filtro();
        filtro.setId(filtroSoap.getId());
        filtro.setIdUsuario(filtroSoap.getIdUsuario());
        filtro.setNombre(filtroSoap.getNombre());
        filtro.setCodigoArticulo(filtroSoap.getCodigoArticulo());
        filtro.setFechaHasta(
                Optional.ofNullable(filtroSoap.getFechaHasta())
                        .map(fecha -> convertToLocalDate(fecha.toGregorianCalendar()))
                        .orElse(null)
        );
        filtro.setFechaDesde(
                Optional.ofNullable(filtroSoap.getFechaDesde())
                        .map(fecha -> convertToLocalDate(fecha.toGregorianCalendar()))
                        .orElse(null)
        );
        filtro.setEstado(filtroSoap.getEstado());

        return filtro;
    }

    public FiltroSoap convertFiltroToFiltroSoap(Filtro filtro) {
        FiltroSoap filtroSoap = new FiltroSoap();
        filtroSoap.setId(filtro.getId());
        filtroSoap.setCodigoArticulo(filtro.getCodigoArticulo());
        filtroSoap.setIdUsuario(filtro.getIdUsuario());
        filtroSoap.setNombre(filtro.getNombre());
        filtroSoap.setEstado(filtro.getEstado());
        filtroSoap.setFechaDesde(convertToXMLGregorianCalendar(filtro.getFechaDesde()));
        filtroSoap.setFechaHasta(convertToXMLGregorianCalendar(filtro.getFechaHasta()));

        return filtroSoap;
    }

    public List<Filtro> convertFiltroSoapToFiltro(List<FiltroSoap> filtroSoapList) {
        List<Filtro> filtro = new ArrayList<Filtro>();
        for (FiltroSoap filtroSoap : filtroSoapList) {
            filtro.add(convertFiltroSoapToFiltro(filtroSoap));
        }
        return filtro;
    }

    public List<FiltroSoap> convertFiltroToFiltroSoap(List<Filtro> filtroList) {
        List<FiltroSoap> filtroSoapList = new ArrayList<FiltroSoap>();
        for (Filtro filtro : filtroList) {
            filtroSoapList.add(convertFiltroToFiltroSoap(filtro));
        }
        return filtroSoapList;
    }

    private static LocalDate convertToLocalDate(GregorianCalendar calendar) {
        Instant instant = calendar.toZonedDateTime().toInstant();
        return instant.atZone(calendar.getTimeZone().toZoneId()).toLocalDate();
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

}
