package com.unla.tienda_service.controller;

import com.unla.tienda_service.dtos.OrdenCompraRequestDto;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.model.ProductoNovedad;
import com.unla.tienda_service.service.OrdenCompraService;
import com.unla.tienda_service.service.ProductoNovedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private ProductoNovedadService productoNovedadService;

    @PostMapping
    public ResponseEntity<OrdenCompra> crearOrden(@RequestBody OrdenCompraRequestDto ordenRequest) {

            OrdenCompra ordenCompra = ordenCompraService.createOrder(ordenRequest);
            return new ResponseEntity<>(ordenCompra, HttpStatus.CREATED);

    }

    @PutMapping("/recibida/{id}")
    public ResponseEntity<OrdenCompra> actualizarOrdenCompra(@PathVariable Long id){
        OrdenCompra ordenCompra = ordenCompraService.changeOrderState(id);

        return new ResponseEntity<>(ordenCompra, HttpStatus.CREATED);
    }

    @GetMapping("/novedades/productos")
    public List<ProductoNovedad> getProductosNovedad(){
        return productoNovedadService.getAllProductosNovedad();
    }
}