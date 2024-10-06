package com.unla.proveedor_service.dtos;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage {

    private Long id;
    private String codigoTienda;
    private String observaciones;
    private LocalDate fechaRecepcion;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;


    public enum EstadoOrden {
        SOLICITADA,
        RECHAZADA,
        ACEPTADA,
        RECIBIDA
    }


}
