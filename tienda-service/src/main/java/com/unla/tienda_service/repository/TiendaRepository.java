package com.unla.tienda_service.repository;

import com.unla.tienda_service.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {

    Optional<Tienda> findByCodigo(String codigo);
    List<Tienda> findByEstado(boolean estado);
}