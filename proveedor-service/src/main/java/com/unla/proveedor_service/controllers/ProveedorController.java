package com.unla.proveedor_service.controllers;


import com.unla.proveedor_service.dtos.ProductResponse;
import com.unla.proveedor_service.models.Product;
import com.unla.proveedor_service.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {


    private final ProductoService productoService;

    @Autowired
    public ProveedorController(ProductoService productoService) {
        this.productoService = productoService;
    }




    @GetMapping("/productos")
    public ResponseEntity<List<ProductResponse>> listarProductos() {
        List<ProductResponse> productos = productoService.getAllProducts();
        return ResponseEntity.ok(productos);
    }

}
