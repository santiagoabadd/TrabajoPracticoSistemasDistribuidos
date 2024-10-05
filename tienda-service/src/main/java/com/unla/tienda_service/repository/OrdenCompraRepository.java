package com.unla.tienda_service.repository;

import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.model.TiendaProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long>{


}
