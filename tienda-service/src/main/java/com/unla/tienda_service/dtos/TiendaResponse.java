package com.unla.tienda_service.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TiendaResponse {

    private Long id;
    private String codigo;
    private Boolean estado;
    private String direccion;
    private String ciudad;
    private String provincia;


}
