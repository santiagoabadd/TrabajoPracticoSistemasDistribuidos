package com.unla.productos_service.service;


import com.unla.productos_service.dtos.ProductResponse;
import com.unla.productos_service.model.Product;
import com.unla.productos_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;




    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return mapToProductResponse(product.get());
    }


    public ProductResponse findByCodigo(String codigo) {
        return mapToProductResponse(productRepository.findByCodigo(codigo).get());
    }



    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .nombre(product.getNombre())
                .codigo(product.getCodigo())
                .foto(product.getFoto())
                .talle(product.getTalle())
                .color(product.getColor())
                .build();
    }
}
