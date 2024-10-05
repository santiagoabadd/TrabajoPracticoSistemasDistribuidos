package com.unla.proveedor_service.repositories;

import com.unla.proveedor_service.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Integer> {
}
