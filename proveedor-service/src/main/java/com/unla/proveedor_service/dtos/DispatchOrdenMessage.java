package com.unla.proveedor_service.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DispatchOrdenMessage {
    private Long dispatchOrdenId;
    private Long ordenId;
    private LocalDateTime fechaEstimadaEnvio;
}
