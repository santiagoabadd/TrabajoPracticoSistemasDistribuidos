package com.unla.users_service.config;

import com.tiendaservice.grpc.TiendaServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel productServiceChannel() {
        return ManagedChannelBuilder.forAddress("127.0.0.1",9091)
                .usePlaintext()
                .build();
    }

    @Bean
    public TiendaServiceGrpc.TiendaServiceBlockingStub tiendaServiceStub(ManagedChannel channel) {
        return TiendaServiceGrpc.newBlockingStub(channel);
    }
}