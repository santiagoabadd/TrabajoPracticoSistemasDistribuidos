package com.unla.tienda_service.service;

import com.unla.tienda_service.messages.DispatchOrdenMessage;
import com.unla.tienda_service.messages.ResponseMessage;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.model.OrdenDespacho;
import com.unla.tienda_service.model.Tienda;
import com.unla.tienda_service.repository.OrdenCompraRepository;
import com.unla.tienda_service.repository.OrdenDespachoRepository;
import com.unla.tienda_service.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenEstadoConsumer {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private OrdenDespachoRepository ordenDespachoRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @KafkaListener(topics = "#{__listener.getDynamicTopics()}", groupId = "tienda-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processResponse(ResponseMessage responseMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            System.out.println("AAAAAAAAAAAAAAAAAA111");

            String codigoTienda = extractCodigoTiendaFromTopic(topic);

            Tienda tienda = tiendaRepository.findByCodigo(codigoTienda).orElse(null);
            if (tienda == null) {
                System.out.println("Tienda no encontrada para el código: " + codigoTienda);
                return;
            }

            System.out.println("Procesando respuesta de la tienda: " + tienda.getCodigo());

            OrdenCompra orden = ordenCompraRepository.findById(responseMessage.getId()).orElse(null);
            if (orden == null) {
                System.out.println("Orden no encontrada: " + responseMessage.getId());
                return;
            }

            orden.setEstado(convertirEstado(responseMessage.getEstado()));
            orden.setFechaRecepcion(responseMessage.getFechaRecepcion());
            orden.setObservaciones(responseMessage.getObservaciones());
            ordenCompraRepository.save(orden);

            System.out.println("Orden actualizada exitosamente para la tienda: " + tienda.getCodigo());

        } catch (Exception e) {
            System.out.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "#{__listener.getDynamicDispatchTopics()}", groupId = "tienda-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processResponseDispatch(DispatchOrdenMessage dispatchOrdenMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            System.out.println("AAAAAAAAAAAAAAAAAA222");

            String codigoTienda = extractCodigoTiendaFromTopic(topic);

            Tienda tienda = tiendaRepository.findByCodigo(codigoTienda).orElse(null);
            if (tienda == null) {
                System.out.println("Tienda no encontrada para el código: " + codigoTienda);
                return;
            }
            System.out.println("Procesando respuesta de la tienda: " + tienda.getCodigo());

            OrdenCompra orden = ordenCompraRepository.findById(dispatchOrdenMessage.getOrdenId()).orElse(null);
            if (orden == null) {
                System.out.println("Orden no encontrada: " + dispatchOrdenMessage.getOrdenId());
                return;
            }

            OrdenDespacho ordenDespacho=new OrdenDespacho();
            ordenDespacho.setFechaEstimadaEnvio(dispatchOrdenMessage.getFechaEstimadaEnvio());
            ordenDespacho.setIdOrdenCompra(orden.getId());

            ordenDespachoRepository.save(ordenDespacho);

            Long idOrdenDespachoGenerado = ordenDespacho.getId();


            orden.setIdOrdenDespacho(idOrdenDespachoGenerado);
            ordenCompraRepository.save(orden);

            System.out.println("Orden actualizada exitosamente para la tienda: " + tienda.getCodigo());

        } catch (Exception e) {
            System.out.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    private OrdenCompra.EstadoOrden convertirEstado(ResponseMessage.EstadoOrden estado) {
        return OrdenCompra.EstadoOrden.valueOf(estado.name());
    }

    private String extractCodigoTiendaFromTopic(String topic) {
        return topic.split("-")[0];
    }


    public List<String> getDynamicTopics() {
        List<Tienda> tiendas = tiendaRepository.findAll();
        return tiendas.stream()
                .map(tienda -> tienda.getCodigo() + "-solicitudes")
                .collect(Collectors.toList());
    }

    public List<String> getDynamicDispatchTopics() {
        List<Tienda> tiendas = tiendaRepository.findAll();
        return tiendas.stream()
                .map(tienda -> tienda.getCodigo() + "-despacho")
                .collect(Collectors.toList());
    }
}
