package com.unla.tienda_service.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordenes_despacho")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenDespacho{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long idOrdenCompra;
    private LocalDate fechaEstimadaEnvio;


}