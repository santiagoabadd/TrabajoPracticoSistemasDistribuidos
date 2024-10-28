package com.unla.productos_service.repository;

import com.unla.productos_service.model.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {


}