package com.unla.tienda_service.messages;

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
