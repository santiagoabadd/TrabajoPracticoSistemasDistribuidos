package com.unla.proveedor_service.services;

import com.unla.proveedor_service.dtos.*;
import com.unla.proveedor_service.models.ItemOrden;
import com.unla.proveedor_service.models.OrdenCompra;
import com.unla.proveedor_service.models.Product;
import com.unla.proveedor_service.repositories.OrdenCompraRepository;
import com.unla.proveedor_service.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProcessingService {

    @Autowired
    private ProductRepository productoRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private KafkaTemplate<String, ResponseMessage> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ProductNovedadMessage> kafkaTemplateNovedad;

    @Autowired
    private KafkaTemplate<String, DispatchOrdenMessage> dispatchKafkaTemplate;

    @KafkaListener(topics = "orden-de-compras", groupId = "proveedor-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processOrder(OrdenCompraMessage orderMessage) {
        System.out.println(orderMessage.getCodigoTienda()+"CODIGO DE TIENDA RECIBIDA");
        System.out.println(orderMessage.getObservaciones()+"ovbs");


        List<String> rejectionReasons = new ArrayList<>();
        List<String> stockIssues = new ArrayList<>();
        OrdenCompra ordenCompra=new OrdenCompra();
        ordenCompra.setId(orderMessage.getId());
        ordenCompra.setCodigoTienda(orderMessage.getCodigoTienda());
        ordenCompra.setFechaSolicitud(orderMessage.getFechaSolicitud());
        ordenCompra.setFechaRecepcion(LocalDate.now());
        ordenCompra.setItems(new ArrayList<ItemOrden>());

        for (ItemOrdenDto item : orderMessage.getItems()) {
            Product producto = productoRepository.findByCodigo(item.getCodigoArticulo()).orElse(null);
            if (producto == null) {
                rejectionReasons.add("Artículo " + item.getCodigoArticulo() + ": no existe");
                continue;
            }
            if (item.getCantidadSolicitada() == null || item.getCantidadSolicitada() < 1) {
                rejectionReasons.add("Artículo " + item.getCodigoArticulo() + ": cantidad mal informada");
                continue;
            }

            if (producto.getStock() < item.getCantidadSolicitada()) {
                stockIssues.add("Artículo " + item.getCodigoArticulo()+ ": stock insuficiente");
            }
        }

        if (!rejectionReasons.isEmpty()) {
            ResponseMessage rejectionResponse = new ResponseMessage();
            rejectionResponse.setId(orderMessage.getId());
            rejectionResponse.setEstado(ResponseMessage.EstadoOrden.RECHAZADA);
            rejectionResponse.setObservaciones(String.join(", ", rejectionReasons));
            rejectionResponse.setFechaRecepcion(LocalDate.now());
            ordenCompra.setObservaciones(String.join(", ", rejectionReasons));
            ordenCompra.setEstado(OrdenCompra.EstadoOrden.RECHAZADA);

            kafkaTemplate.send(orderMessage.getCodigoTienda() + "-solicitudes", rejectionResponse);

        } else if (!stockIssues.isEmpty()) {
            ResponseMessage stockIssueResponse = new ResponseMessage();
            stockIssueResponse.setId(orderMessage.getId());
            stockIssueResponse.setEstado(ResponseMessage.EstadoOrden.ACEPTADA);
            stockIssueResponse.setObservaciones(String.join(", ", stockIssues));
            stockIssueResponse.setFechaRecepcion(LocalDate.now());
            ordenCompra.setObservaciones(String.join(", ", stockIssues));
            ordenCompra.setEstado(OrdenCompra.EstadoOrden.ACEPTADA);

            kafkaTemplate.send(orderMessage.getCodigoTienda() + "-solicitudes", stockIssueResponse);

        } else {
            ResponseMessage acceptanceResponse = new ResponseMessage();
            acceptanceResponse.setId(orderMessage.getId());
            acceptanceResponse.setEstado(ResponseMessage.EstadoOrden.ACEPTADA);
            acceptanceResponse.setObservaciones("Orden aceptada sin problemas de stock.");
            acceptanceResponse.setFechaRecepcion(LocalDate.now());
            ordenCompra.setObservaciones("Orden aceptada sin problemas de stock.");
            ordenCompra.setEstado(OrdenCompra.EstadoOrden.ACEPTADA);

            System.out.println(acceptanceResponse.getEstado()+"ORDEN NUEVA ESTADO");
            System.out.println(acceptanceResponse.getFechaRecepcion()+"ORDEN NUEVA FECHA RECEPCION");

            kafkaTemplate.send(orderMessage.getCodigoTienda()+ "-solicitudes", acceptanceResponse);


            DispatchOrdenMessage dispatchOrden = new DispatchOrdenMessage();
            dispatchOrden.setOrdenId(orderMessage.getId());
            dispatchOrden.setFechaEstimadaEnvio(LocalDate.now().plusDays(3));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dispatchKafkaTemplate.send(orderMessage.getCodigoTienda() + "-despacho", dispatchOrden);

            for (ItemOrdenDto item : orderMessage.getItems()) {
                Product producto = productoRepository.findByCodigo(item.getCodigoArticulo()).orElseThrow(()->new RuntimeException("No se encontro un producto con ese codigo"));
                producto.setStock(producto.getStock() - item.getCantidadSolicitada());
                productoRepository.save(producto);
            }

        }

        if(!ordenCompra.getEstado().equals(OrdenCompra.EstadoOrden.RECHAZADA)){
            for (ItemOrdenDto item : orderMessage.getItems()) {
                ordenCompra.getItems().add(new ItemOrden(item.getId(),item.getCodigoArticulo(),item.getColor(),item.getTalle(),item.getCantidadSolicitada(),ordenCompra));
            }
        }
        System.out.println("-------->"+ordenCompra.getObservaciones());
        ordenCompraRepository.save(ordenCompra);

    }

    @KafkaListener(topics = "recepcion", groupId = "proveedor-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processOrderReception(ChangeOrderMessage changOrderMessage){

        System.out.println("AAAAAAAAAAAAAAAAAA222");

        OrdenCompra orden = ordenCompraRepository.findById(changOrderMessage.getId()).orElse(null);
        if (orden == null) {
            System.out.println("Orden no encontrada: " + changOrderMessage.getId());
            return;
        }


        orden.setEstado(OrdenCompra.EstadoOrden.RECIBIDA);
        System.out.println(orden.toString());
        ordenCompraRepository.save(orden);


    }

    public ProductNovedadMessage sendProductoNovedad(ProductResponse productResponse){

        ProductNovedadMessage productNovedadMessage=new ProductNovedadMessage();

        productNovedadMessage.setCodigo(productResponse.getCodigo());
        productNovedadMessage.setNombre(productResponse.getNombre());
        productNovedadMessage.setFoto(productResponse.getFoto());
        productNovedadMessage.setTalle(productResponse.getTalle());
        productNovedadMessage.setColor(productResponse.getColor());

        kafkaTemplateNovedad.send("novedades",productNovedadMessage);

        return productNovedadMessage;
    }




}