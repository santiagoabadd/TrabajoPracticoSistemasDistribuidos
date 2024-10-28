package com.unla.tienda_service.model;

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
@ToString
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoTienda;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
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