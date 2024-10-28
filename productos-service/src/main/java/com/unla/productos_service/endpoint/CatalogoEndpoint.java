package com.unla.productos_service.endpoint;


import com.unla.productos_service.converter.CatalogoConverter;
import com.unla.productos_service.model.Catalogo;
import com.unla.productos_service.model.Product;
import com.unla.productos_service.repository.CatalogoRepository;

import com.unla.productos_service.repository.ProductRepository;
import com.unla.productos_service.util.PdfExportService;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

@Endpoint
public class CatalogoEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PdfExportService pdfExportService;

    @Autowired
    private CatalogoConverter catalogoConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogoRequest")
    @ResponsePayload
    public GetCatalogoResponse getCatalogoResponse(@RequestPayload GetCatalogoRequest request) {
        GetCatalogoResponse response = new GetCatalogoResponse();


       List<CatalogoSoap> catalogoSoapList = catalogoConverter.convertCatalogoListToCatalogoSoapList(catalogoRepository.findByIdTienda(request.getIdTienda()));

        response.getCatalogo().addAll(catalogoSoapList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postCatalogoRequest")
    @ResponsePayload
    public PostCatalogoResponse postProducts(@RequestPayload PostCatalogoRequest request) {

        PostCatalogoResponse response = new PostCatalogoResponse();
        Catalogo catalogo = catalogoConverter.convertCatalogoSoapToCatalogo(request.getCatalogo());

        CatalogoSoap catalogoSoap = catalogoConverter.convertCatalogoToCatalogoSoap(catalogoRepository.save(catalogo));

        response.setCatalogo(catalogoSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addProductsToCatalogoRequest")
    @ResponsePayload
    public AddProductsToCatalogoResponse addProductsToCatalogo(@RequestPayload AddProductsToCatalogoRequest request) {
        AddProductsToCatalogoResponse response = new AddProductsToCatalogoResponse();

        Optional<Catalogo> catalogoOpt = catalogoRepository.findById(request.getId());

        if (catalogoOpt.isPresent()) {
            Catalogo catalogo = catalogoOpt.get();

            List<Product> productsToAdd = new ArrayList<>();
            for (ProductSoap productSoap : request.getProducts()) {
                Optional<Product> productOpt = productRepository.findById(productSoap.getId());
                productOpt.ifPresent(productsToAdd::add);
            }

            catalogo.getProducts().addAll(productsToAdd);

            catalogoRepository.save(catalogo);

            CatalogoSoap catalogoSoap = catalogoConverter.convertCatalogoToCatalogoSoap(catalogo);
            response.setCatalogo(catalogoSoap);
        } else {
            throw new RuntimeException("Catalogo not found with ID: " + request.getId());
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogoPdfRequest")
    @ResponsePayload
    public GetCatalogoPdfResponse getCatalogoPdf(@RequestPayload GetCatalogoPdfRequest request) {
        GetCatalogoPdfResponse response = new GetCatalogoPdfResponse();

        Catalogo catalogo = catalogoRepository.findById(request.getCatalogoId())
                .orElseThrow(() -> new RuntimeException("Catalogo no encontrado"));

        try {
            byte[] pdfData = pdfExportService.exportCatalogoToPdf(catalogo);
            response.setPdfData(Base64.getEncoder().encodeToString(pdfData));
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarCatalogoRequest")
    @ResponsePayload
    public EliminarCatalogoResponse deleteCatalogo(@RequestPayload EliminarCatalogoRequest request) {
        EliminarCatalogoResponse response = new EliminarCatalogoResponse();

        Optional<Catalogo> catalogoOpt = catalogoRepository.findById(request.getIdCatalogo());

        if (catalogoOpt.isPresent()) {
            catalogoRepository.delete(catalogoOpt.get());
            response.setResultado(true);
        } else {
            response.setResultado(false);
        }

        return response;
    }

}
