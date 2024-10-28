package com.unla.productos_service.converter;


import com.unla.productos_service.model.Catalogo;
import com.unla.productos_service.model.Product;
import com.unla.productos_service.repository.ProductRepository;
import io.spring.guides.gs_producing_web_service.CatalogoSoap;
import io.spring.guides.gs_producing_web_service.ProductSoap;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CatalogoConverter {

    @Autowired
    private ProductRepository productRepository;


    public Catalogo convertCatalogoSoapToCatalogo(CatalogoSoap catalogoSoap) {
        Catalogo catalogo = new Catalogo();
        catalogo.setId(catalogoSoap.getId());
        catalogo.setNombre(catalogoSoap.getNombre());
        catalogo.setIdTienda(catalogoSoap.getIdTienda());
        List<Product> products = new ArrayList<>();
        for (ProductSoap productSoap : catalogoSoap.getProducts()) {
            Product existingProduct = productRepository.findById(productSoap.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Product not found with ID: " + productSoap.getId())
            );
            products.add(existingProduct);
        }
        catalogo.setProducts(products);

        return catalogo;
    }

    public CatalogoSoap convertCatalogoToCatalogoSoap(Catalogo catalogo) {
        CatalogoSoap catalogoSoap = new CatalogoSoap();
        catalogoSoap.setId(catalogo.getId());
        catalogoSoap.setNombre(catalogo.getNombre());
        catalogoSoap.setIdTienda(catalogo.getIdTienda());
        List<ProductSoap> products = catalogoSoap.getProducts();
        products.clear();
        products.addAll(this.mapProductsSoap(catalogo.getProducts()));
        return catalogoSoap;
    }

    public List<Catalogo> convertCatalogoSoapListToCatalogoList(List<CatalogoSoap> catalogoSoapList) {
        List<Catalogo> catalogo = new ArrayList<Catalogo>();
        for (CatalogoSoap catalogoSoap : catalogoSoapList) {
            catalogo.add(convertCatalogoSoapToCatalogo(catalogoSoap));
        }
        return catalogo;
    }

    public List<CatalogoSoap> convertCatalogoListToCatalogoSoapList(List<Catalogo> catalogoList) {
        catalogoList.forEach(oc -> Hibernate.initialize(oc.getProducts()));
        List<CatalogoSoap> catalogoSoapList = new ArrayList<CatalogoSoap>();
        for (Catalogo catalogo : catalogoList) {
            catalogoSoapList.add(convertCatalogoToCatalogoSoap(catalogo));
        }
        return catalogoSoapList;
    }

    private static LocalDate convertToLocalDate(GregorianCalendar calendar) {
        Instant instant = calendar.toZonedDateTime().toInstant();
        return instant.atZone(calendar.getTimeZone().toZoneId()).toLocalDate();
    }

    public List<Product> mapProducts(List<ProductSoap> productSoapList) {
        return productSoapList.stream()
                .map(this::mapSingleProduct)
                .collect(Collectors.toList());
    }

    public List<ProductSoap> mapProductsSoap(List<Product> productList) {
        return productList.stream()
                .map(this::mapSingleProductSoap)
                .collect(Collectors.toList());
    }

    private Product mapSingleProduct(ProductSoap productSoap) {
        Product product = new Product();
        product.setId(productSoap.getId());
        product.setCodigo(productSoap.getCodigo());
        product.setFoto(productSoap.getFoto());
        product.setTalle(productSoap.getTalle());
        product.setColor(productSoap.getColor());
        product.setNombre(productSoap.getNombre());
        return product;
    }

    private ProductSoap mapSingleProductSoap(Product product) {
        ProductSoap productSoap = new ProductSoap();
        productSoap.setId(product.getId());
        productSoap.setCodigo(product.getCodigo());
        productSoap.setFoto(product.getFoto());
        productSoap.setTalle(product.getTalle());
        productSoap.setColor(product.getColor());
        productSoap.setNombre(product.getNombre());
        return productSoap;
    }

    public XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        try {
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
