package com.unla.tienda_service.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="producto-novedad")
public class ProductoNovedad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String codigo;
    private String foto;
    private String color;
    private String talle;
    private Integer stock;


}

