package com.unla.productos_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name= "catalogo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Catalogo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private String nombre;

        private long idTienda;

        @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
        @JoinTable(
                name = "catalogo_producto",
                joinColumns = @JoinColumn(name = "catalogo_id"),
                inverseJoinColumns = @JoinColumn(name = "producto_id")
        )
        private List<Product> products;




}
