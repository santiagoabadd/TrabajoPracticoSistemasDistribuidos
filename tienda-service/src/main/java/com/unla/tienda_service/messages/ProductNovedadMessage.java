package com.unla.tienda_service.messages;


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
