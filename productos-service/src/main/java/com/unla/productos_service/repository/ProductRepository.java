package com.unla.productos_service.repository;

import com.unla.productos_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCodigo(String codigo);
    Optional<Product> findByNombre(String nombre);
    void deleteByCodigo(String codigo);

}