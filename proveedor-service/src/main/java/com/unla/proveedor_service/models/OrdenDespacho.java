package com.unla.proveedor_service.models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ordenes_despacho")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenDespacho {

    @Id
    private Long id;

    private long idOrdenCompra;


    private LocalDate fechaEstimadaEnvio;


}