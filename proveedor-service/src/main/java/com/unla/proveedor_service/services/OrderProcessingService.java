package com.unla.proveedor_service.services;

import com.unla.proveedor_service.dtos.DispatchOrdenMessage;
import com.unla.proveedor_service.dtos.ItemOrdenDto;
import com.unla.proveedor_service.dtos.OrdenCompraMessage;
import com.unla.proveedor_service.dtos.ResponseMessage;
import com.unla.proveedor_service.models.Product;
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
    private KafkaTemplate<String, ResponseMessage> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DispatchOrdenMessage> dispatchKafkaTemplate;

    @KafkaListener(topics = "orden-de-compra", groupId = "proveedor-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processOrder(OrdenCompraMessage orderMessage) {
        List<String> rejectionReasons = new ArrayList<>();
        List<String> stockIssues = new ArrayList<>();

        // Validar cada artículo en la orden
        for (ItemOrdenDto item : orderMessage.getItems()) {
            // Verificar si el producto existe
            Product producto = productoRepository.findByCodigo(item.getCodigoArticulo()).orElseThrow(()->new RuntimeException("No se encontro un producto con ese codigo"));
            if (producto == null) {
                rejectionReasons.add("Artículo " + item.getCodigoArticulo() + ": no existe");
                continue;
            }

            // Verificar si la cantidad solicitada es válida
            if (item.getCantidadSolicitada() == null || item.getCantidadSolicitada() < 1) {
                rejectionReasons.add("Artículo " + item.getCodigoArticulo() + ": cantidad mal informada");
                continue;
            }

            // Verificar el stock
            if (producto.getStock() < item.getCantidadSolicitada()) {
                stockIssues.add("Artículo " + item.getCodigoArticulo()+ ": stock insuficiente");
            }
        }

        if (!rejectionReasons.isEmpty()) {
            // Caso a: Orden RECHAZADA
            ResponseMessage rejectionResponse = new ResponseMessage();
            rejectionResponse.setId(orderMessage.getId());
            rejectionResponse.setEstado("RECHAZADA");
            rejectionResponse.setObservaciones(String.join(", ", rejectionReasons));

            kafkaTemplate.send("/" + orderMessage.getCodigoTienda() + "/solicitudes", rejectionResponse);
        } else if (!stockIssues.isEmpty()) {
            // Caso b: Orden ACEPTADA con problemas de stock
            ResponseMessage stockIssueResponse = new ResponseMessage();
            stockIssueResponse.setId(orderMessage.getId());
            stockIssueResponse.setEstado("ACEPTADA");
            stockIssueResponse.setObservaciones(String.join(", ", stockIssues));

            kafkaTemplate.send("/" + orderMessage.getCodigoTienda() + "/solicitudes", stockIssueResponse);

            // Internamente, la orden queda pausada hasta que se actualice el stock
            // Puedes guardar esta información en una base de datos o gestionar el estado de otra manera
        } else {
            // Caso c: Orden ACEPTADA completamente
            ResponseMessage acceptanceResponse = new ResponseMessage();
            acceptanceResponse.setId(orderMessage.getId());
            acceptanceResponse.setEstado("ACEPTADA");
            acceptanceResponse.setObservaciones("Orden aceptada sin problemas de stock.");

            kafkaTemplate.send("/" + orderMessage.getCodigoTienda()+ "/solicitudes", acceptanceResponse);

            // Generar una orden de despacho
            DispatchOrdenMessage dispatchOrder = new DispatchOrdenMessage();
            dispatchOrder.setDispatchOrdenId(generateDispatchOrderId()); // Implementa este método
            dispatchOrder.setOrdenId(orderMessage.getId());
            dispatchOrder.setFechaEstimadaEnvio(LocalDateTime.now().plusDays(3)); // Fecha estimada de envío

            kafkaTemplate.send("/" + orderMessage.getCodigoTienda() + "/despacho", new ResponseMessage());

            // Actualizar el stock
            for (ItemOrdenDto item : orderMessage.getItems()) {
                Product producto = productoRepository.findByCodigo(item.getCodigoArticulo()).orElseThrow(()->new RuntimeException("No se encontro un producto con ese codigo"));
                producto.setStock(producto.getStock() - item.getCantidadSolicitada());
                productoRepository.save(producto);
            }
        }
    }

    private Long generateDispatchOrderId() {
        // Implementa la lógica para generar un ID único para la orden de despacho
        return System.currentTimeMillis(); // Ejemplo simple
    }
}