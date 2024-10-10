package com.unla.tienda_service.service;

import com.unla.tienda_service.dtos.ProductChangeDto;
import com.unla.tienda_service.messages.OrdenCompraMessage;
import com.unla.tienda_service.messages.ProductNovedadMessage;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.model.ProductoNovedad;
import com.unla.tienda_service.repository.ProductoNovedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CrearProductoNovedadConsumer {

    @Autowired
    private ProductoNovedadRepository productoNovedadRepository;

    @Autowired
    private KafkaTemplate<String, OrdenCompraMessage> kafkaTemplate;



    @KafkaListener(topics = "novedades", groupId = "tienda-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processResponse(ProductNovedadMessage productNovedadMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        ProductoNovedad productoNovedad=new ProductoNovedad();
        productoNovedad.setCodigo(productNovedadMessage.getCodigo());
        productoNovedad.setTalle(productNovedadMessage.getTalle());
        productoNovedad.setColor(productNovedadMessage.getColor());
        productoNovedad.setNombre(productNovedadMessage.getNombre());
        productoNovedad.setFoto(productNovedadMessage.getFoto());

        productoNovedadRepository.save(productoNovedad);
    }
}
