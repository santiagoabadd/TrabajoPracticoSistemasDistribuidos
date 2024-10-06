package com.unla.tienda_service.service;


import com.unla.tienda_service.dtos.ProductChangeDto;
import com.unla.tienda_service.messages.OrdenCompraMessage;
import com.unla.tienda_service.messages.ResponseMessage;
import com.unla.tienda_service.model.OrdenCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdenProductChangeConsumer {

    private final OrdenCompraService ordenCompraService;
    @Autowired
    private KafkaTemplate<String, OrdenCompraMessage> kafkaTemplate;

    @Autowired
    public OrdenProductChangeConsumer(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }

    @KafkaListener(topics = "product-change", groupId = "tienda-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processResponse(ProductChangeDto productChange, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        List<OrdenCompra> ordenes=ordenCompraService.getAllPausedByProduct(productChange.getCodigoArticulo());

        for(int i=0;i<ordenes.size();i++){
            kafkaTemplate.send("orden-de-compras",ordenes.get(i));
        }
    }
}
