package com.unla.tienda_service.service;

import com.unla.tienda_service.messages.ResponseMessage;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdenEstadoConsumer {

    @Autowired
    private OrdenCompraRepository orderRepository;

    @KafkaListener(topics = "/{codigo de tienda}/solicitudes", groupId = "proveedor-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processResponse(ResponseMessage responseMessage) {

        System.out.println("Procesando respuesta de la orden: " + responseMessage);


    }


}
