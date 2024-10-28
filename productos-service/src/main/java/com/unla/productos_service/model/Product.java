package com.unla.productos_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String codigo;
    private String foto;
    private String color;
    private String talle;

    @ManyToMany(mappedBy = "products")
    private List<Catalogo> catalogos = new ArrayList<>();



}
