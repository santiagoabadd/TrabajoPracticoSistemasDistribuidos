package com.unla.proveedor_service.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemOrdenDto {

    private String codigoArticulo;
    private String color;
    private String talle;
    private Integer cantidadSolicitada;
}
