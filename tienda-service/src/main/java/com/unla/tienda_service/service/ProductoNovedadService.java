package com.unla.tienda_service.service;


import com.tiendaservice.grpc.*;
import com.unla.tienda_service.model.ProductoNovedad;
import com.unla.tienda_service.repository.ProductoNovedadRepository;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class ProductoNovedadService extends NovedadesServiceGrpc.NovedadesServiceImplBase{

    private final ProductoNovedadRepository productoNovedadRepository;


    @Override
    public void listarNovedades(EmptyNovedad empty, StreamObserver<ListarNovedadesResponse> responseObserver){
        List<ProductoNovedad> novedades =productoNovedadRepository.findAll();

        List<NovedadesInfo> novedadesInfos=novedades.stream()
                .map(novedad -> NovedadesInfo.newBuilder()
                        .setId(novedad.getId())
                        .setCodigo(novedad.getCodigo())
                        .setNombre(novedad.getNombre())
                        .setColor(novedad.getColor())
                        .setFoto(novedad.getFoto())
                        .setTalle(novedad.getTalle())
                        .build()
                ).collect(Collectors.toList());

        ListarNovedadesResponse response = ListarNovedadesResponse.newBuilder()
                .addAllNovedades(novedadesInfos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void deleteNovedad(DeleteNovedadRequest request, StreamObserver<DeleteNovedadResponse> responseObserver) {
        Long id = request.getId();
        boolean success;
        String message;

        try {
            productoNovedadRepository.deleteById(id);
            success = true;
            message = "Novedad eliminada con éxito";
        } catch (Exception e) {
            success = false;
            message = "Error al eliminar la Novedad: " + e.getMessage();
        }

        DeleteNovedadResponse response = DeleteNovedadResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
