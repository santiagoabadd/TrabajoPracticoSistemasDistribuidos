package com.unla.users_service.converts;


import com.unla.users_service.dtos.UserParseError;
import io.spring.guides.gs_producing_web_service.UserParseErrorSoap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserBulkUploadConverter {
    /*
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
 */
    public UserParseErrorSoap convertUserParseErrorModelToSoap(UserParseError error) {
        UserParseErrorSoap userParseErrorSoap = new UserParseErrorSoap();
        userParseErrorSoap.setUserName(error.getUserName());
        userParseErrorSoap.setErrorMessage(error.getErrorMessage());
        userParseErrorSoap.setLineNumber(error.getLineNumber());

      return userParseErrorSoap;
    }

    public List<UserParseErrorSoap> convertUserParseErrorListToSoap(List<UserParseError> errors) {
        return errors.stream()
                .map(this::convertUserParseErrorModelToSoap)
                .collect(Collectors.toList());
    }

    }


/*
    public List<CatalogoSoap> convertCatalogoListToCatalogoSoapList(List<Catalogo> catalogoList) {
        catalogoList.forEach(oc -> Hibernate.initialize(oc.getProducts()));
        List<CatalogoSoap> catalogoSoapList = new ArrayList<CatalogoSoap>();
        for (Catalogo catalogo : catalogoList) {
            catalogoSoapList.add(convertCatalogoToCatalogoSoap(catalogo));
        }
        return catalogoSoapList;
    }
*/

