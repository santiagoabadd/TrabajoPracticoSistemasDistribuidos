package com.unla.proveedor_service.controllers;


import com.unla.proveedor_service.dtos.ProductRequest;
import com.unla.proveedor_service.dtos.ProductResponse;
import com.unla.proveedor_service.models.Product;
import com.unla.proveedor_service.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/productos/{id}/{stock}")
    public ResponseEntity<ProductResponse> actualizarStock(@PathVariable Long id, @PathVariable Integer stock) {
        ProductResponse updatedProduct = productoService.updateStock(id, stock);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/productos/create")
    public ResponseEntity<ProductResponse> crearProducto(@RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productoService.createProduct(productRequest);
        return ResponseEntity.ok(updatedProduct);
    }



}
