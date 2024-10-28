package com.unla.productos_service.service;


import com.unla.productos_service.model.Product;
import com.unla.productos_service.repository.ProductRepository;
import com.productoservice.grpc.*;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class ProductServiceRPC extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository productRepository;

    @Override
    public void getAllProducts(EmptyProduct request, StreamObserver<GetAllProductsResponse> responseObserver) {
        List<Product> products = productRepository.findAll();

        List<ProductInfo> productInfoList = products.stream()
                .map(product -> ProductInfo.newBuilder()
                        .setId(product.getId())
                        .setCodigo(product.getCodigo())
                        .setNombre(product.getNombre())
                        .setFoto(product.getFoto())
                        .setColor(product.getColor())
                        .setTalle(product.getTalle())
                        .build())
                .collect(Collectors.toList());

        GetAllProductsResponse response = GetAllProductsResponse.newBuilder()
                .addAllProductsInfo(productInfoList)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<GetProductResponse> responseObserver) {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        GetProductResponse response = GetProductResponse.newBuilder()
                .setId(product.getId())
                .setCodigo(product.getCodigo())
                .setNombre(product.getNombre())
                .setFoto(product.getFoto())
                .setColor(product.getColor())
                .setTalle(product.getTalle())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByNombre(GetProductByNombreRequest request, StreamObserver<GetProductResponse> responseObserver) {
        Product product = productRepository.findByNombre(request.getNombre())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        GetProductResponse response = GetProductResponse.newBuilder()
                .setId(product.getId())
                .setCodigo(product.getCodigo())
                .setNombre(product.getNombre())
                .setFoto(product.getFoto())
                .setColor(product.getColor())
                .setTalle(product.getTalle())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByCode(GetProductByCodeRequest request, StreamObserver<GetProductResponse> responseObserver) {
        Product product = productRepository.findByCodigo(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        GetProductResponse response = GetProductResponse.newBuilder()
                .setId(product.getId())
                .setCodigo(product.getCodigo())
                .setNombre(product.getNombre())
                .setFoto(product.getFoto())
                .setColor(product.getColor())
                .setTalle(product.getTalle())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void createProduct(CreateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = new Product();
        product.setCodigo(request.getCodigo());
        product.setNombre(request.getNombre());
        product.setFoto(request.getFoto());
        product.setTalle(request.getTalle());
        product.setColor(request.getTalle());


        productRepository.save(product);

        ProductResponse response = ProductResponse.newBuilder()
                .setId(product.getId())
                .setCodigo(product.getCodigo())
                .setNombre(product.getNombre())
                .setFoto(product.getFoto())
                .setColor(product.getColor())
                .setTalle(product.getTalle())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateProduct(UpdateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = productRepository.findByCodigo(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setNombre(request.getNombre());
        product.setFoto(request.getFoto());
        product.setTalle(request.getTalle());
        product.setColor(request.getTalle());

        productRepository.save(product);

        ProductResponse response = ProductResponse.newBuilder()
                .setId(product.getId())
                .setCodigo(product.getCodigo())
                .setNombre(product.getNombre())
                .setFoto(product.getFoto())
                .setColor(product.getColor())
                .setTalle(product.getTalle())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void deleteProduct(DeleteProductRequest request, StreamObserver<DeleteProductResponse> responseObserver) {
        boolean success;
        String message;

        try {
            productRepository.deleteById(request.getId());
            success = true;
            message = "Producto eliminado con éxito";
        } catch (Exception e) {
            success = false;
            message = "Error al eliminar el producto: " + e.getMessage();
        }

        DeleteProductResponse response = DeleteProductResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
