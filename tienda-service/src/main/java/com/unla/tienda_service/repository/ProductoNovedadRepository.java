package com.unla.tienda_service.repository;

import com.unla.tienda_service.model.ProductoNovedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoNovedadRepository extends JpaRepository<ProductoNovedad,Long> {
}
