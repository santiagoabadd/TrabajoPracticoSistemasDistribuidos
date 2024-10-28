package com.unla.soap_client.controller;


import com.unla.soap_client.client.SoapClient;
import com.unla.soap_client.service1.*;
import com.unla.soap_client.service2.*;
import com.unla.soap_client.service3.*;
import com.unla.soap_client.service4.UserBulkUploadRequest;
import com.unla.soap_client.service4.UserBulkUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


//LINK PARA ACCEDER A LA DOCUMENTACION SWAGGER
//http://localhost:8085/swagger-ui/index.html

@CrossOrigin("*")
@RestController
public class ClientController {


    private final SoapClient soapClientService1;
    private final SoapClient soapClientService2;
    private final SoapClient soapClientService3;
    private final SoapClient soapClientService4;

    @Autowired
    public ClientController(@Qualifier("soapClientService1") SoapClient soapClientService1,
                            @Qualifier("soapClientService2") SoapClient soapClientService2,
                            @Qualifier("soapClientService3") SoapClient soapClientService3,
                            @Qualifier("soapClientService4") SoapClient soapClientService4)
    {
        this.soapClientService1 = soapClientService1;
        this.soapClientService2 = soapClientService2;
        this.soapClientService3 = soapClientService3;
        this.soapClientService4 = soapClientService4;
    }

    @Operation(summary = "Obtiene una lista de las ordenes, si el GetOrdenComprasRequest esta vacio trae todas, estos son los campos si se quiere filtrar  {codigoArticulo: 'MDZ3ZAIMLA', fechaDesde: '2024-10-11', fechaHasta: '2024-10-16', estado: 'ACEPTADA', codigoTienda: 'T001'}", description = "Este endpoint devuelve una lista completa o filtrada de las ordenes agrupadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/getAllOrders")
    public GetOrdenComprasAgrupadasResponse getAllOrders(@RequestBody GetOrdenComprasRequest getOrdenComprasRequest) {



        GetOrdenComprasAgrupadasResponse getOrdenComprasAgrupadasResponse = soapClientService1.getOrdenComprasAgrupadasResponse(getOrdenComprasRequest);


        return getOrdenComprasAgrupadasResponse;
    }

    @Operation(summary = "Obtiene una lista de las ordenes por codigo de tienda", description = "Este endpoint devuelve una lista completade las ordenes que pertenecen a una tienda agrupadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/getAllOrdersByTienda/{codigoTienda}")
    public GetOrdenComprasByTiendaResponse getAllOrdersByTienda(@PathVariable String codigoTienda) {
        GetOrdenComprasByTiendaRequest getOrdenComprasByTiendaRequest=new GetOrdenComprasByTiendaRequest();
        getOrdenComprasByTiendaRequest.setCodigoTienda(codigoTienda);
        GetOrdenComprasByTiendaResponse getOrdenComprasByTiendaResponse = soapClientService1.getOrdenComprasByTiendaResponse(getOrdenComprasByTiendaRequest);


        return getOrdenComprasByTiendaResponse;
    }

    @Operation(summary = "Obtiene una lista de los catalogos de una tienda, la request es asi {idTienda: 'idTienda'} ", description = "Este endpoint devuelve una lista de los catalogos que pertenecen a una tienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/getAllCatalogos")
    public GetCatalogoResponse getAllCatalogos(@RequestBody GetCatalogoRequest getCatalogoRequest){

        GetCatalogoResponse getCatalogoResponse = soapClientService2.getCatalogoResponse(getCatalogoRequest);


        return getCatalogoResponse;
    }

    @Operation(summary = "Metodo para agreagar un catalogo nuevo con ningun o muchos productos adentro ", description = "Este endpoint devuelve el catalogo creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/postCatalogo")
    public PostCatalogoResponse postCatalogoResponse(@RequestBody CatalogoSoap catalogoSoap) {

        PostCatalogoResponse postCatalogoResponse = soapClientService2.postCatalogoResponse(catalogoSoap);

        return postCatalogoResponse;
    }

    @Operation(summary = "Metodo para agreagar productos a un catalogo , se necesita la id, y una lista de los productos" , description = "Este endpoint devuelve el catalogo con los productos que contiende")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/addProductsToCatalogo")
    public AddProductsToCatalogoResponse addProductsToCatalogoResponse (@RequestBody CatalogoSoap catalogoSoap) {

        AddProductsToCatalogoResponse addProductsToCatalogoResponse = soapClientService2.addProductsToCatalogoResponse(catalogoSoap);


        return addProductsToCatalogoResponse;
    }
    @Operation(summary = "este metodo imprime un catalogo a pdf, pasandole la id del catalogo en la request de la api" , description = "Este endpoint devuelve una cadena en Base64, que puede ser convertida a pdf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/PrintCatalogoPdf/{idCatalogo}")
    public GetCatalogoPdfResponse printCatalogoPdf (@PathVariable Long idCatalogo) {
        GetCatalogoPdfRequest getCatalogoPdfRequest=new GetCatalogoPdfRequest();
        getCatalogoPdfRequest.setCatalogoId(idCatalogo);
        GetCatalogoPdfResponse getCatalogoPdfResponse = soapClientService2.getCatalogoPdfResponse(getCatalogoPdfRequest);

        return getCatalogoPdfResponse;
    }

    @Operation(summary = "este metodo trae todos los filtros de un usuario" , description = "este metodo trae todos los filtros de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/getAllFiltros")
    public ObtenerFiltrosResponse obtenerFiltrosResponse(@RequestBody ObtenerFiltrosRequest obtenerFiltrosRequest) {

        ObtenerFiltrosResponse obtenerFiltrosResponse = soapClientService3.obtenerFiltrosResponse(obtenerFiltrosRequest);


        return obtenerFiltrosResponse;
    }

    @Operation(summary = "este metodo es para agreagar un filtro a la base de datos" , description = "devuelve el filtro creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/addFiltro")
    public PostFiltroResponse postFiltroResponse (@RequestBody FiltroSoap filtroSoap) {

        PostFiltroResponse postFiltroResponse = soapClientService3.postFiltroResponse(filtroSoap);


        return postFiltroResponse;
    }

    @Operation(summary = "este metodo es para cargare masivamente muchos usuarios mediante un arhivos csv, en la request el archivo debe ser una cadena en Base64" , description = "devuelve los errores que hubo al crear usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/addUsersCsv")
    public UserBulkUploadResponse userBulkUploadResponse (@RequestBody UserBulkUploadRequest userBulkUploadRequest) {

        UserBulkUploadResponse userBulkUploadResponse = soapClientService4.userBulkUploadResponse(userBulkUploadRequest);


        return userBulkUploadResponse;
    }

    @Operation(summary = "este metodo es para eliminar un catalogo mediante su id," , description = "devuelve true o false dependiendo si lo pudo eliminar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminarCatalogo/{id}")
    public EliminarCatalogoResponse eliminarCatalogoResponse (@PathVariable Long id) {

        EliminarCatalogoRequest eliminarCatalogoRequest=new EliminarCatalogoRequest();
        eliminarCatalogoRequest.setIdCatalogo(id);
        EliminarCatalogoResponse eliminarCatalogoResponse = soapClientService2.eliminarCatalogoResponse(eliminarCatalogoRequest);


        return eliminarCatalogoResponse;
    }

    @Operation(summary = "este metodo es para eliminar un filtro mediante su id," , description = "devuelve true o false dependiendo si lo pudo eliminar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminarFiltro/{id}")
    public EliminarFiltroResponse eliminarFiltroResponse (@PathVariable Long id) {

        EliminarFiltroRequest eliminarFiltroRequest=new EliminarFiltroRequest();
        eliminarFiltroRequest.setId(id);
        EliminarFiltroResponse eliminarFiltroResponse = soapClientService3.eliminarFiltroResponse(id);


        return eliminarFiltroResponse;
    }

    @Operation(summary = "Este método es para modificar un filtro", description = "Modifica un filtro existente con los datos proporcionados en el cuerpo de la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtro modificado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/modificarFiltro")
    public ModificarFiltroResponse modificarFiltroResponse(@RequestBody FiltroSoap filtroSoap) {

        ModificarFiltroRequest modificarFiltroRequest = new ModificarFiltroRequest();
        modificarFiltroRequest.setFiltro(filtroSoap);

        ModificarFiltroResponse modificarFiltroResponse = soapClientService3.modificarFiltroResponse(filtroSoap);

        return modificarFiltroResponse;
    }


}
