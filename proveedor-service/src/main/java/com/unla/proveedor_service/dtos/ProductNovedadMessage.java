package com.unla.proveedor_service.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductNovedadMessage {

    private String codigo;
    private String nombre;
    private String foto;
    private String color;
    private String talle;

}
