package com.unla.tienda_service.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "items_orden")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoArticulo;
    private String color;
    private String talle;
    private Integer cantidadSolicitada;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id")
    private OrdenCompra ordenCompra;
}