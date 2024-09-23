package com.unla.tienda_service.repository;


import com.unla.tienda_service.model.TiendaProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TiendaProductRepository extends JpaRepository<TiendaProduct, Long> {


}