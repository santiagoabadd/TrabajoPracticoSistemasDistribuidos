package com.unla.proveedor_service.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String nombre;
    private String codigo;
    private String foto;
    private String color;
    private String talle;
    private Integer stock;

}