package com.unla.proveedor_service.services;

import com.unla.proveedor_service.dtos.DispatchOrdenMessage;
import com.unla.proveedor_service.dtos.OrdenCompraMessage;
import com.unla.proveedor_service.dtos.ProductChangeDto;
import com.unla.proveedor_service.dtos.ResponseMessage;
import com.unla.proveedor_service.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrdersPausedProcessingService {


    @Autowired
    private KafkaTemplate<String, ProductChangeDto> productChangeKafkaTemplate;

    public Integer enviarProductosActualizados(Product product){

        ProductChangeDto productChange=new ProductChangeDto();

        productChange.setStock(product.getStock());
        productChange.setCodigoArticulo(product.getCodigo());

        productChangeKafkaTemplate.send("product-change",productChange);

        return productChange.getStock();

    }



    }
