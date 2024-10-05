package com.unla.tienda_service.service;

import com.unla.tienda_service.dtos.ItemOrdenDto;
import com.unla.tienda_service.dtos.OrdenCompraRequestDto;
import com.unla.tienda_service.messages.OrdenCompraMessage;
import com.unla.tienda_service.model.ItemOrden;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrdenCompraMessage> kafkaTemplate;

    public OrdenCompra createOrder(OrdenCompraRequestDto ordenRequest) {
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setCodigoTienda(ordenRequest.getCodigoTienda());
        ordenCompra.setEstado(OrdenCompra.EstadoOrden.SOLICITADA);
        ordenCompra.setFechaSolicitud(LocalDate.now());

        List<ItemOrden> items = new ArrayList<>();
        for (ItemOrdenDto itemDTO : ordenRequest.getItems()) {
            ItemOrden item = new ItemOrden();
            item.setCodigoArticulo(itemDTO.getCodigoArticulo());
            item.setColor(itemDTO.getColor());
            item.setTalle(itemDTO.getTalle());
            item.setCantidadSolicitada(itemDTO.getCantidadSolicitada());
            item.setOrdenCompra(ordenCompra);
            items.add(item);
        }
        ordenCompra.setItems(items);

        orderRepository.save(ordenCompra);


        OrdenCompraMessage message = new OrdenCompraMessage();
        message.setCodigoTienda(ordenCompra.getCodigoTienda());
        message.setId(ordenCompra.getId());
        message.setItems(ordenRequest.getItems());
        message.setFechaSolicitud(ordenCompra.getFechaSolicitud());

        kafkaTemplate.send("orden-de-compra", message);

        return ordenCompra;
    }
}
