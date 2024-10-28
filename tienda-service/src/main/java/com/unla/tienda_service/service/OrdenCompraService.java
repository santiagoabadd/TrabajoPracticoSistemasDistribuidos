package com.unla.tienda_service.service;

import com.productoservice.grpc.GetProductByCodeRequest;
import com.productoservice.grpc.GetProductResponse;
import com.productoservice.grpc.ProductServiceGrpc;
import com.unla.tienda_service.dtos.ItemOrdenDto;
import com.unla.tienda_service.dtos.OrdenCompraRequestDto;
import com.unla.tienda_service.messages.ChangeOrderMessage;
import com.unla.tienda_service.messages.OrdenCompraMessage;
import com.unla.tienda_service.model.ItemOrden;
import com.unla.tienda_service.model.OrdenCompra;
import com.unla.tienda_service.model.Tienda;
import com.unla.tienda_service.model.TiendaProduct;
import com.unla.tienda_service.repository.OrdenCompraRepository;
import com.unla.tienda_service.repository.OrdenDespachoRepository;
import com.unla.tienda_service.repository.TiendaProductRepository;
import com.unla.tienda_service.repository.TiendaRepository;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.server.service.GrpcService;
import com.tiendaservice.grpc.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class OrdenCompraService extends OrdenServiceGrpc.OrdenServiceImplBase{

    @Autowired
    private KafkaTemplate<String, ChangeOrderMessage> kafkaTemplateChange;

    @Autowired
    private OrdenCompraRepository orderRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private OrdenDespachoRepository ordenDespachoRepository;


    @Autowired
    private TiendaProductRepository tiendaProductRepository;

    @Autowired
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Autowired
    private KafkaTemplate<String, OrdenCompraMessage> kafkaTemplate;

    @Override
    @Transactional
    public void createOrder(CreateOrderRequest ordenRequest, StreamObserver<CreateOrderResponse> responseObserver) {
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setCodigoTienda(ordenRequest.getCodigoTienda());
        ordenCompra.setEstado(OrdenCompra.EstadoOrden.SOLICITADA);
        ordenCompra.setFechaSolicitud(LocalDate.now());

        List<ItemOrden> items = new ArrayList<>();
        for (ItemOrdenDtoGrpc itemDTO : ordenRequest.getItemOrdenDtoGrpcList()) {
            ItemOrden item = new ItemOrden();
            item.setCodigoArticulo(itemDTO.getCodigoArticulo());
            item.setColor(itemDTO.getColor());
            item.setTalle(itemDTO.getTalle());
            item.setCantidadSolicitada(itemDTO.getCantidadSolicitada());
            item.setOrdenCompra(ordenCompra);
            items.add(item);
        }
        ordenCompra.setItems(items);

        orderRepository.save(ordenCompra);

        List<ItemOrdenDto> itemsDto = new ArrayList<>();
        for (ItemOrdenDtoGrpc itemDTO : ordenRequest.getItemOrdenDtoGrpcList()) {
            ItemOrdenDto item = new ItemOrdenDto();
            item.setCodigoArticulo(itemDTO.getCodigoArticulo());
            item.setColor(itemDTO.getColor());
            item.setTalle(itemDTO.getTalle());
            item.setCantidadSolicitada(itemDTO.getCantidadSolicitada());
            itemsDto.add(item);
        }

        OrdenCompraMessage orderMessage = new OrdenCompraMessage();
        orderMessage.setCodigoTienda(ordenCompra.getCodigoTienda());
        orderMessage.setId(ordenCompra.getId());
        orderMessage.setItems(itemsDto);
        orderMessage.setFechaSolicitud(ordenCompra.getFechaSolicitud());

        kafkaTemplate.send("orden-de-compras",orderMessage);


    }

    @Override
    @Transactional
    public void changeOrderState(ChangeOrderStateRequest ordenRequest, StreamObserver<ChangeOrderStateResponse> responseObserver){
        OrdenCompra ordenCompra=orderRepository.findById(ordenRequest.getId()).orElseThrow(()-> new RuntimeException("No existe una orden de compra con ese id"));
        ordenCompra.setEstado(OrdenCompra.EstadoOrden.RECIBIDA);
        ChangeOrderMessage changeOrderMessage=new ChangeOrderMessage();
        changeOrderMessage.setFechaRecepcion(LocalDate.now());
        changeOrderMessage.setId(ordenRequest.getId());
        Tienda tienda=tiendaRepository.findByCodigo(ordenCompra.getCodigoTienda()).orElseThrow(()->new RuntimeException("Tienda no encontrada con el codigo"+ordenCompra.getCodigoTienda()));
        List<ItemOrden> items=ordenCompra.getItems();
        for(ItemOrden item : items){
            GetProductByCodeRequest productRequest = GetProductByCodeRequest.newBuilder()
                    .setCodigo(item.getCodigoArticulo())
                    .build();


            GetProductResponse productResponse= productServiceStub.getProductByCode(productRequest);
            TiendaProduct tiendaProduct=tiendaProductRepository.findByTiendaIdAndProductId(tienda.getId(),productResponse.getId()).orElseThrow(()->new RuntimeException("Producto no encontrado en esta tienda"));
            tiendaProduct.setStock(tiendaProduct.getStock()+item.getCantidadSolicitada());
            tiendaProductRepository.save(tiendaProduct);
        }
        kafkaTemplateChange.send("recepcion",changeOrderMessage);
        orderRepository.save(ordenCompra);
    }

    @Override
    @Transactional
    public void listarOrdenesByTienda(ListarOrdenesRequest request, StreamObserver<ListarOrdenesResponse> responseObserver){
        List<OrdenCompra> ordenes=orderRepository.findByCodigoTienda(request.getCodigoTienda());

        //ListarOrdenesResponse.Builder responseBuilder= ListarOrdenesResponse.newBuilder();

        List<OrdenesInfo> ordenesInfo = ordenes.stream()

                .map(orden -> {

                    OrdenDespacho ordenDespacho = obtenerOrdenDespacho(orden.getIdOrdenDespacho());

                    return OrdenesInfo.newBuilder()
                            .setCodigoTienda(orden.getCodigoTienda())
                            .setEstado(orden.getEstado().toString())
                            .setObservaciones(orden.getObservaciones())
                            .setFechaSolicitud(orden.getFechaSolicitud().toString())
                            .setFechaRecepcion(orden.getFechaRecepcion().toString())


                            .build();
                })
                .collect(Collectors.toList());

        for(OrdenCompra ordenCompra: ordenes){

        }
    }




    public List<OrdenCompra> getAllPausedByProduct(String codigoProducto){
        List<OrdenCompra> ordenes= orderRepository.findAll();
        List<OrdenCompra> ordenesPausadas=new ArrayList<OrdenCompra>();
        if(!ordenes.isEmpty()){
            for(int i=0;i<ordenes.size();i++){
                if(ordenes.get(i).getIdOrdenDespacho()==null && ordenes.get(i).getEstado()== OrdenCompra.EstadoOrden.ACEPTADA){
                    for(int j=0;j<ordenes.get(i).getItems().size();j++){
                        if(ordenes.get(i).getItems().get(j).getCodigoArticulo().equals(codigoProducto)){
                            ordenesPausadas.add(ordenes.get(i));
                        }
                    }
                }
            }
        }

        return ordenesPausadas;
    }

    private OrdenDespacho obtenerOrdenDespacho(Long id) {

       //OrdenDespacho ordenDespacho=ordenDespachoRepository.findById(id).orElseThrow( ()-> new RuntimeException("asfaf"));

        return OrdenDespacho.newBuilder()
                .setFechaEstimadaEnvio("2024-10-20") // Ajusta esto según tu lógica
                .build();
    }



}
