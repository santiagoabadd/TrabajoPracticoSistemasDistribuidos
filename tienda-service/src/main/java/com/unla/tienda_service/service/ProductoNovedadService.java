package com.unla.tienda_service.service;


import com.unla.tienda_service.model.ProductoNovedad;
import com.unla.tienda_service.repository.ProductoNovedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoNovedadService {

    private final ProductoNovedadRepository productoNovedadRepository;

    @Autowired
    public ProductoNovedadService(ProductoNovedadRepository productoNovedadRepository) {
        this.productoNovedadRepository = productoNovedadRepository;
    }

    public List<ProductoNovedad> getAllProductosNovedad(){
        return productoNovedadRepository.findAll();
    }
}
