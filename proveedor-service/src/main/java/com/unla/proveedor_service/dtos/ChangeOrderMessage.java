package com.unla.proveedor_service.dtos;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeOrderMessage {
    private Long id;
    private LocalDate fechaRecepcion;
}
