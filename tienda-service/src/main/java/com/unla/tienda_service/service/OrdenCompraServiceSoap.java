package com.unla.tienda_service.service;


import com.unla.tienda_service.converter.OrdenCompraConverter;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.repository.OrdenCompraRepository;
import io.spring.guides.gs_producing_web_service.EstadoOrden;
import io.spring.guides.gs_producing_web_service.ItemOrdenSoap;
import io.spring.guides.gs_producing_web_service.OrdenCompraResumenSoap;
import io.spring.guides.gs_producing_web_service.OrdenCompraSoap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenCompraServiceSoap {
    private final OrdenCompraRepository ordenCompraRepository;
    private final OrdenCompraConverter ordenCompraConverter;



        public List<OrdenCompraSoap> getOrdenCompras(String codigoTienda, String codigoArticulo, Date fechaDesde, Date fechaHasta, String estado) {
            List<OrdenCompraSoap> ordenesFiltradas = new ArrayList<>();
            List<OrdenCompra> ordenesCompra = ordenCompraRepository.findAll();
            for (OrdenCompraSoap orden : ordenCompraConverter.convertOrdenCompraToOrdenCompraSoapList(ordenesCompra)) {
                boolean coincide = true;

                Date fechaSolicitud = toDate(orden.getFechaSolicitud());
                System.out.println("Fecha solicitud de orden: " + fechaSolicitud);

                if (codigoTienda != null && !codigoTienda.isEmpty() && !orden.getCodigoTienda().equalsIgnoreCase(codigoTienda)) {
                    coincide = false;
                }
                if (codigoArticulo != null && !codigoArticulo.isEmpty()) {
                    boolean articuloCoincide = false;
                    for (ItemOrdenSoap item : orden.getItems()) {
                        if (item.getCodigoArticulo().equalsIgnoreCase(codigoArticulo)) {
                            articuloCoincide = true;
                            break;
                        }
                    }
                    if (!articuloCoincide) {
                        coincide = false;
                    }
                }

                if (fechaDesde != null) {
                    System.out.println("Comparando fechaDesde: " + fechaDesde + " con fechaSolicitud: " + fechaSolicitud);
                    if (fechaSolicitud != null && (fechaSolicitud.before(fechaDesde) && !fechaSolicitud.equals(fechaDesde))) {
                        coincide = false;
                        System.out.println("Fecha fuera de rango desde: " + fechaSolicitud);
                    }
                }
                if (fechaHasta != null) {
                    System.out.println("Comparando fechaHasta: " + fechaHasta + " con fechaSolicitud: " + fechaSolicitud);
                    if (fechaSolicitud != null && (fechaSolicitud.after(fechaHasta) && !fechaSolicitud.equals(fechaHasta))) {
                        coincide = false;
                        System.out.println("Fecha fuera de rango hasta: " + fechaSolicitud);
                    }
                }
                if (estado != null && !estado.isEmpty() && !orden.getEstado().toString().equalsIgnoreCase(estado)) {
                    coincide = false;
                }

                if (coincide) {
                    ordenesFiltradas.add(orden);
                }
            }



            return ordenesFiltradas;
        }

    public List<OrdenCompraResumenSoap> getOrdenComprasResumen(List<OrdenCompra> ordenesCompra) {
        Map<List<Object>, Integer> groupedData = ordenesCompra.stream()
                .flatMap(orden -> orden.getItems().stream()
                        .map(item -> {
                            List<Object> key = Arrays.asList(
                                    item.getCodigoArticulo(),
                                    orden.getEstado(),
                                    orden.getCodigoTienda()
                            );
                            return new AbstractMap.SimpleEntry<>(key, item.getCantidadSolicitada());
                        }))
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey(),
                        Collectors.summingInt(entry -> entry.getValue())
                ));

        // Transformación de los datos agrupados en objetos OrdenCompraResumenSoap
        return groupedData.entrySet().stream()
                .map(entry -> {
                    OrdenCompraResumenSoap resumen = new OrdenCompraResumenSoap();
                    List<Object> keyList = entry.getKey();

                    resumen.setCodigoArticulo((String) keyList.get(0));
                    resumen.setEstado((EstadoOrden) keyList.get(1));
                    resumen.setCodigoTienda((String) keyList.get(2));
                    resumen.setTotalCantidadSolicitada(entry.getValue());

                    return resumen;
                })
                .collect(Collectors.toList());
    }

    public static Date toDate(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        return xmlGregorianCalendar.toGregorianCalendar().getTime();
    }
}