package com.unla.tienda_service.messages;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DispatchOrdenMessage {
    private Long ordenId;
    private LocalDate fechaEstimadaEnvio;
}
