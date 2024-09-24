package com.unla.tienda_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "tienda-product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TiendaProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tiendaId;
    private Long productId;


    private Integer stock;



}
