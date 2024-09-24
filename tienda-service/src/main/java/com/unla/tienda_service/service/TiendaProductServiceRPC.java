package com.unla.tienda_service.service;

import com.unla.tienda_service.model.TiendaProduct;
import com.unla.tienda_service.repository.TiendaProductRepository;
import com.tiendaservice.grpc.*;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class TiendaProductServiceRPC extends TiendaProductServiceGrpc.TiendaProductServiceImplBase {

    private final TiendaProductRepository tiendaProductRepository;


    @Override
    @Transactional
    public void addTiendaProduct(AddTiendaProductRequest request, StreamObserver<AddTiendaProductResponse> responseObserver) {

        System.out.println(request);
        TiendaProduct tiendaProduct = TiendaProduct.builder()
                .tiendaId(request.getTiendaId())
                .productId(request.getProductId())
                .stock(request.getStock())
                .color(request.getColor())
                .talle(request.getTalle())
                .build();

        tiendaProductRepository.save(tiendaProduct);

        AddTiendaProductResponse response = AddTiendaProductResponse.newBuilder()
                .setMessage("Producto agregado a la tienda")
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTiendaProduct(GetTiendaProductRequest request, StreamObserver<GetTiendaProductResponse> responseObserver) {
        TiendaProduct tiendaProduct = tiendaProductRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("TiendaProduct no encontrado"));

        GetTiendaProductResponse response = GetTiendaProductResponse.newBuilder()
                .setTiendaId(tiendaProduct.getTiendaId())
                .setProductId(tiendaProduct.getProductId())
                .setStock(tiendaProduct.getStock())
                .setColor(tiendaProduct.getColor())
                .setTalle(tiendaProduct.getTalle())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTiendaProduct(DeleteTiendaProductRequest request, StreamObserver<DeleteTiendaProductResponse> responseObserver) {
        tiendaProductRepository.deleteById(request.getId());

        DeleteTiendaProductResponse response = DeleteTiendaProductResponse.newBuilder()
                .setMessage("TiendaProduct eliminado")
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}