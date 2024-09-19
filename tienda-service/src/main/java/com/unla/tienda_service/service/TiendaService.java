package com.unla.tienda_service.service;


import com.unla.tienda_service.dtos.TiendaResponse;
import com.unla.tienda_service.model.Tienda;
import com.unla.tienda_service.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TiendaService {


    private final TiendaRepository tiendaRepository;




    public List<TiendaResponse> getAllTiendas() {
        var clients = tiendaRepository.findAll();
        return clients.stream().map(this::mapToTiendaResponse).toList();
    }

    public TiendaResponse getById(Long id) {
        Optional<Tienda> tienda = tiendaRepository.findById(id);
        return mapToTiendaResponse(tienda.get());
    }


    public TiendaResponse findByCodigo(String codigo) {
        return mapToTiendaResponse(tiendaRepository.findByCodigo(codigo).get());
    }



    private TiendaResponse mapToTiendaResponse(Tienda tienda) {
        return TiendaResponse.builder()
                .id(tienda.getId())
                .codigo(tienda.getCodigo())
                .estado(tienda.getEstado())
                .build();
    }
}
