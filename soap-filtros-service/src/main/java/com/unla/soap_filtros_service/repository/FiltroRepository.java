package com.unla.soap_filtros_service.repository;


import com.unla.soap_filtros_service.model.Filtro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FiltroRepository extends JpaRepository<Filtro, Long> {
    List<Filtro> findByNombre(String nombre);
    List<Filtro> findByIdUsuario(long idUsuario);

}