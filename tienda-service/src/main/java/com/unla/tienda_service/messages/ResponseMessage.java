package com.unla.tienda_service.messages;


import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage {

    private Long id;
    private String codigoTienda;
    private String observaciones;
    private String estado;


}
