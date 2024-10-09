package com.unla.tienda_service.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemOrdenDto {

    private long id;
    private String codigoArticulo;
    private String color;
    private String talle;
    private Integer cantidadSolicitada;
}
