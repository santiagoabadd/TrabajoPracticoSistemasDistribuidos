package com.unla.tienda_service.service;

import com.productoservice.grpc.GetProductRequest;
import com.productoservice.grpc.GetProductResponse;
import com.productoservice.grpc.ProductServiceGrpc;
import com.unla.tienda_service.model.TiendaProduct;
import com.unla.tienda_service.repository.TiendaProductRepository;
import com.tiendaservice.grpc.*;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;


import java.util.List;


@GrpcService
@RequiredArgsConstructor
public class TiendaProductServiceRPC extends TiendaProductServiceGrpc.TiendaProductServiceImplBase {

    private final TiendaProductRepository tiendaProductRepository;
    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Override
    @Transactional
    public void addTiendaProduct(AddTiendaProductRequest request, StreamObserver<AddTiendaProductResponse> responseObserver) {

        System.out.println(request);
        TiendaProduct tiendaProduct = TiendaProduct.builder()
                .tiendaId(request.getTiendaId())
                .productId(request.getProductId())
                .stock(request.getStock())
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


    @Override
    @Transactional
    public void obtenerProductosPorTienda(TiendaProductsRequest request, StreamObserver<TiendaProductsResponse> responseObserver) {
        long tiendaId = request.getTiendaId();
        List<TiendaProduct> productosEnTienda = tiendaProductRepository.findByTiendaId(tiendaId);

        TiendaProductsResponse.Builder responseBuilder = TiendaProductsResponse.newBuilder();

        for (TiendaProduct tiendaProduct : productosEnTienda) {

            GetProductRequest productRequest = GetProductRequest.newBuilder()
                    .setId(tiendaProduct.getProductId())
                    .build();

            GetProductResponse productResponse = productServiceStub.getProduct(productRequest);


            TiendaProductResponse tiendaProductResponse = TiendaProductResponse.newBuilder()
                    .setTiendaId(tiendaProduct.getTiendaId())
                    .setProductoId(tiendaProduct.getProductId())
                    .setNombre(productResponse.getNombre())
                    .setCodigo(productResponse.getCodigo())
                    .setFoto(productResponse.getFoto())
                    .setColor(productResponse.getColor())
                    .setTalle(productResponse.getTalle())
                    .setStock(tiendaProduct.getStock())
                    .build();


            responseBuilder.addProductos(tiendaProductResponse);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void obtenerProductos(EmptyTienda request, StreamObserver<TiendaProductsResponse> responseObserver) {

        List<TiendaProduct> productosEnTienda = tiendaProductRepository.findAll();
        TiendaProductsResponse.Builder responseBuilder = TiendaProductsResponse.newBuilder();

        for (TiendaProduct tiendaProduct : productosEnTienda) {

            GetProductRequest productRequest = GetProductRequest.newBuilder()
                    .setId(tiendaProduct.getProductId())
                    .build();

            GetProductResponse productResponse = productServiceStub.getProduct(productRequest);

            TiendaProductResponse tiendaProductResponse = TiendaProductResponse.newBuilder()
                    .setTiendaId(tiendaProduct.getTiendaId())
                    .setProductoId(tiendaProduct.getProductId())
                    .setNombre(productResponse.getNombre())
                    .setCodigo(productResponse.getCodigo())
                    .setFoto(productResponse.getFoto())
                    .setColor(productResponse.getColor())
                    .setTalle(productResponse.getTalle())
                    .setStock(tiendaProduct.getStock())
                    .build();


            responseBuilder.addProductos(tiendaProductResponse);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void asociarProductos(AsociarProductosRequest request, StreamObserver<AsociarProductosResponse> responseObserver) {
        long productId = request.getProductId();
        List<Long> tiendaIds = request.getTiendaIdsList();

        tiendaProductRepository.deleteByProductIdAndTiendaIdNotIn(productId, tiendaIds);

        if (tiendaIds.isEmpty()) {
            tiendaProductRepository.deleteByProductId(productId);
        } else {
            for (Long tiendaId : tiendaIds) {

                boolean exists = tiendaProductRepository.existsByTiendaIdAndProductId(tiendaId, productId);

                if (!exists) {
                    TiendaProduct tiendaProduct = TiendaProduct.builder()
                            .tiendaId(tiendaId)
                            .productId(productId)
                            .stock(0)
                            .build();
                    tiendaProductRepository.save(tiendaProduct);
                }
            }
        }

        AsociarProductosResponse response = AsociarProductosResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Asociación de productos realizada correctamente")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}