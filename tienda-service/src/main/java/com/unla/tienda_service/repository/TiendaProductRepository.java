package com.unla.tienda_service.repository;


import com.unla.tienda_service.model.TiendaProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TiendaProductRepository extends JpaRepository<TiendaProduct, Long> {
    List<TiendaProduct > findByTiendaId(Long tiendaId);

}