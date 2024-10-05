package com.unla.proveedor_service.dtos;


import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenCompraMessage {

    private Long id;
    private String codigoTienda;
    private String observaciones;
    private List<ItemOrdenDto> items;
    private LocalDate fechaSolicitud;
}
