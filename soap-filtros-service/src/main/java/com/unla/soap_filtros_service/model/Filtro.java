package com.unla.soap_filtros_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name= "filtro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filtro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private long idUsuario;
    private String codigoArticulo;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String estado;

}
