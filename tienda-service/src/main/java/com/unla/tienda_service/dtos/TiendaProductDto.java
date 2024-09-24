package com.unla.tienda_service.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TiendaProductDto {


        private long productoId;
        private String nombre;
        private String codigo;
        private String foto;
        private String color;
        private String talle;
        private long tiendaId;
        private int stock;


}
