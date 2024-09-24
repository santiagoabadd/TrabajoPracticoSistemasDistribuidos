package com.unla.tienda_service.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private long id;
    private String codigo;
    private String nombre;
    private String foto;
    private String color;
    private String talle;
}
