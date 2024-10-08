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
import java.util.*;

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


        OrdenCompraMessage orderMessage = new OrdenCompraMessage();
        orderMessage.setCodigoTienda(ordenCompra.getCodigoTienda());
        orderMessage.setId(ordenCompra.getId());
        orderMessage.setItems(ordenRequest.getItems());
        orderMessage.setFechaSolicitud(ordenCompra.getFechaSolicitud());

        kafkaTemplate.send("orden-de-compras",orderMessage);

        return ordenCompra;
    }



    public List<OrdenCompra> getAllPausedByProduct(String codigoProducto){
        List<OrdenCompra> ordenes= orderRepository.findAll();
        List<OrdenCompra> ordenesPausadas=new ArrayList<OrdenCompra>();
        if(!ordenes.isEmpty()){
            for(int i=0;i<ordenes.size();i++){
                if(ordenes.get(i).getIdOrdenDespacho()==null && ordenes.get(i).getEstado()== OrdenCompra.EstadoOrden.ACEPTADA){
                    for(int j=0;j<ordenes.get(i).getItems().size();j++){
                        if(ordenes.get(i).getItems().get(j).getCodigoArticulo().equals(codigoProducto)){
                            ordenesPausadas.add(ordenes.get(i));
                        }
                    }
                }
            }
        }

        return ordenesPausadas;
    }
}
