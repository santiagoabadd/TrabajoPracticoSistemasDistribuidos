package com.unla.tienda_service.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenCompraRequestDto {

    private String codigoTienda;
    private String observaciones;
    private List<ItemOrdenDto> items;
}
