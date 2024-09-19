package com.unla.tienda_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "tienda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String codigo;
    private Boolean estado;
    private String direccion;
    private String ciudad;
    private String provincia;




}
