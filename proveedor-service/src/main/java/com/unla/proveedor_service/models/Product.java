package com.unla.proveedor_service.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="producto")
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String codigo;
    private String foto;
    private String color;
    private String talle;
    private Integer stock;


    public Product(String nombre, String codigo, String foto, String color, String talle, Integer stock) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.foto = foto;
        this.color = color;
        this.talle = talle;
        this.stock = stock;
    }
}
