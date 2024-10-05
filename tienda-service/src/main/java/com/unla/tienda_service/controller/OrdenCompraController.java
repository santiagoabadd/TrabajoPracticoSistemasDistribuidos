package com.unla.tienda_service.controller;

import com.unla.tienda_service.dtos.OrdenCompraRequestDto;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordenes-compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @PostMapping
    public ResponseEntity<OrdenCompra> crearOrden(@RequestBody OrdenCompraRequestDto ordenRequest) {
        try {
            OrdenCompra ordenCompra = ordenCompraService.createOrder(ordenRequest);
            return new ResponseEntity<>(ordenCompra, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}