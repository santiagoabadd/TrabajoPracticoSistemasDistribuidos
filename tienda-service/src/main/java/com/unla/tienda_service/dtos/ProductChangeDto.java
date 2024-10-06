package com.unla.tienda_service.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductChangeDto {

    private String codigoArticulo;
    private Integer stock;
}
