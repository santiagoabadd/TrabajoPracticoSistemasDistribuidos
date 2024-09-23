package com.unla.tienda_service.service;

import com.unla.tienda_service.model.Tienda;
import com.unla.tienda_service.repository.TiendaRepository;
import com.unla.tienda_service.dtos.TiendaResponse;
import com.tiendaservice.grpc.*;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;


@GrpcService
@RequiredArgsConstructor
public class TiendaServiceRPC extends TiendaServiceGrpc.TiendaServiceImplBase {


    private final TiendaRepository tiendaRepository;

    @Override
    public void getTienda(GetTiendaRequest request, StreamObserver<GetTiendaResponse> responseObserver) {
        Tienda tienda = tiendaRepository.findByCodigo(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));;

        GetTiendaResponse response = GetTiendaResponse.newBuilder()
                .setCodigo(tienda.getCodigo())
                .setEstado(tienda.getEstado())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void registrarTienda(RegistrarTiendaRequest request, StreamObserver<RegistrarTiendaResponse> responseObserver) {
        Tienda tienda=new Tienda();
        tienda.setCodigo(request.getCodigo());
        tienda.setCiudad(request.getCiudad());
        tienda.setDireccion(request.getDireccion());
        tienda.setEstado(request.getEstado());
        tienda.setProvincia(request.getProvincia());




        String message;
        if (isTiendaExists(tienda.getCodigo())) {
            message = "Esa tienda ya existe";
        } else {
            tiendaRepository.save(tienda);
        }

        RegistrarTiendaResponse response = RegistrarTiendaResponse.newBuilder()
                .setCodigo(tienda.getCodigo())
                .setCiudad(tienda.getCiudad())
                .setDireccion(tienda.getDireccion())
                .setEstado(tienda.getEstado())
                .setProvincia(tienda.getProvincia())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private boolean isTiendaExists(String codigo) {
        return tiendaRepository.findByCodigo(codigo).isPresent();
    }

    @Override
    @Transactional
    public void updateTienda(UpdateTiendaRequest request, StreamObserver<UpdateTiendaResponse> responseObserver) {
        String codigo =request.getCodigo();
        Tienda tienda=tiendaRepository.findByCodigo(codigo).orElseThrow(() -> new RuntimeException("Tienda no encontrada"));;

        tienda.setCodigo(request.getCodigo());
        tienda.setCiudad(request.getCiudad());
        tienda.setDireccion(request.getDireccion());
        tienda.setEstado(request.getEstado());
        tienda.setProvincia(request.getProvincia());


        tiendaRepository.save(tienda);

        UpdateTiendaResponse response = UpdateTiendaResponse.newBuilder()
                .setCodigo(tienda.getCodigo())
                .setCiudad(tienda.getCiudad())
                .setDireccion(tienda.getDireccion())
                .setEstado(tienda.getEstado())
                .setProvincia(tienda.getProvincia())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void deleteTienda(DeleteTiendaRequest request, StreamObserver<DeleteTiendaResponse> responseObserver) {
        Long id = request.getId();
        boolean success;
        String message;

        try {
            tiendaRepository.deleteById(id);
            success = true;
            message = "Tienda eliminada con éxito";
        } catch (Exception e) {
            success = false;
            message = "Error al eliminar la tienda: " + e.getMessage();
        }

        DeleteTiendaResponse response = DeleteTiendaResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listarTiendas(EmptyTienda request, StreamObserver<ListarTiendasResponse> responseObserver) {
        List<Tienda> tiendas = tiendaRepository.findAll();

        List<TiendaInfo> tiendasInfo= tiendas.stream()
                .map(tienda -> TiendaInfo.newBuilder()
                        .setCodigo(tienda.getCodigo())
                        .setCiudad(tienda.getCiudad())
                        .setDireccion(tienda.getDireccion())
                        .setEstado(tienda.getEstado())
                        .setProvincia(tienda.getProvincia())
                        .build())
                .collect(Collectors.toList());

        ListarTiendasResponse response = ListarTiendasResponse.newBuilder()
                .addAllTiendasInfo(tiendasInfo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTiendasByEstado(GetTiendaByEstadoRequest request, StreamObserver<ListarTiendasResponse> responseObserver) {
        List<Tienda> tiendas = tiendaRepository.findByEstado(request.getEstado());

        List<TiendaInfo> tiendasInfo= tiendas.stream()
                .map(tienda -> TiendaInfo.newBuilder()
                        .setCodigo(tienda.getCodigo())
                        .setCiudad(tienda.getCiudad())
                        .setDireccion(tienda.getDireccion())
                        .setEstado(tienda.getEstado())
                        .setProvincia(tienda.getProvincia())
                        .build())
                .collect(Collectors.toList());

        ListarTiendasResponse response = ListarTiendasResponse.newBuilder()
                .addAllTiendasInfo(tiendasInfo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}