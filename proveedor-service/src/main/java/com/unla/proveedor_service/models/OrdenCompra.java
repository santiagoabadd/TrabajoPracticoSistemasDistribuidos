package com.unla.proveedor_service.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordenes_compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenCompra {

    @Id
    private Long id;
    private String codigoTienda;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_compra_id")
    private List<ItemOrden> items;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    private String observaciones;

    private Long idOrdenDespacho;

    private LocalDate fechaSolicitud;

    private LocalDate fechaRecepcion;


    public enum EstadoOrden {
        SOLICITADA,
        RECHAZADA,
        ACEPTADA,
        RECIBIDA
    }
}