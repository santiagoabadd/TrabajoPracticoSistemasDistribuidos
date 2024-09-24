package com.unla.tienda_service.service;

import com.unla.tienda_service.repository.TiendaProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TiendaProductService {

    @Autowired
    private TiendaProductRepository productTiendaRepository;



}
